package com.besuikerd.autologistics.tile.traits

import com.besuikerd.autologistics.lib.dsl.vm._
import com.besuikerd.autologistics.tile.TileEntityMod
import com.besuikerd.autologistics.lib.inventory._
import net.minecraft.inventory.{ISidedInventory, IInventory}
import net.minecraft.item.{ItemStack, ItemBlock, Item}
import net.minecraft.util.EnumFacing
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import scala.collection.mutable.{Map => MMap, ArrayBuffer}

trait TileLogistic extends TileEntityMod{
  this: TileEntityMod
    with TileCable
    with TileVirtualMachine =>

  virtualMachine.addNative("_getItem", nativeGetItem)
  virtualMachine.addNative("_itemFilter", nativeItemFilter)
  virtualMachine.addNative("_transferTo", nativeTransferTo)

  val directions = Map(
    "north" -> EnumFacing.NORTH,
    "east" -> EnumFacing.EAST,
    "south" -> EnumFacing.SOUTH,
    "west" -> EnumFacing.WEST,
    "up" -> EnumFacing.UP,
    "down" -> EnumFacing.DOWN
  )
  directions.keys.foreach(dir => virtualMachine.globals.put(dir, StringValue(dir)))

  def nativeGetItem(values:List[StackValue]): StackValue = {
    checkArgCount(values, 2)
    val (mod :: xs) = values
    val (name :: _) = xs
    ObjectValue(MMap(
      "filter" -> ObjectValue(MMap(
        "sides" -> ListValue(),
        "items" -> ListValue()
      )),
      "mod" -> mod,
      "name" -> name
    ))
  }

  def nativeItemFilter(values:List[StackValue]): StackValue = {
    checkArgCount(values, 2)
    values match{
      case List(item:ObjectValue, filters:ListValue, _*)=> {
        val itemCopy = item.copy()
        for{
          ObjectValue(filterMapping) <- itemCopy.mapping.get("filter")
          ListValue(items) <- filterMapping.get("items")
          ListValue(sides) <- filterMapping.get("sides")
        } {
          val itemFilters = filters.list.collect{case o@ObjectValue(mapping) if mapping.contains("mod") && mapping.contains("name") => o}
          val sideFilters = filters.list.collect{case v@StringValue(s) if directions.contains(s) => v}
          items ++= itemFilters
          sides ++= sideFilters
        }
        for{n@RealNumber(_) <- itemCopy.mapping.get("amount")}{itemCopy.mapping.put("amount", n)}
        itemCopy
      }
      case other => throw NativeFunctionException(s"invalid arguments for item filter: $values")
    }
  }

  def nativeTransferTo(values:List[StackValue]): StackValue = {

    checkArgCount(values, 2)
    val itemFrom = values(0)
    val itemTo = values(1)
    itemFrom match{
      case ObjectValue(fromMapping) => {
        val fromSuccess = for {
          StringValue(mod) <- fromMapping.get("mod")
          StringValue(name) <- fromMapping.get("name")
          ObjectValue(fromFilter) <- fromMapping.get("filter")
        } yield {
            val fromBlock = Block.getBlockFromName(mod + ":" + name)
            if (fromBlock == null) {
              throw new NativeFunctionException(s"Could not find block $mod:$name")
            } else {
              itemTo match {
                case ObjectValue(toMapping) => {
                  val toSuccess = for {
                    StringValue(mod) <- toMapping.get("mod")
                    StringValue(name) <- toMapping.get("name")
                    ObjectValue(toFilter) <- toMapping.get("filter")
                  } yield {
                    val toBlock = Block.getBlockFromName(mod + ":" + name)
                    if(toBlock == null){
                      throw new NativeFunctionException(s"Could not find block $mod:$name")
                    } else{
                      val inventories = findInventories
                      val fromInventories = inventories.filter(_.getBlockType.equals(fromBlock))
                      val toInventories = inventories.filter(_.getBlockType.equals(toBlock))
                      if(fromInventories.nonEmpty && toInventories.nonEmpty){
                        val transfer = for{
                          (fromInv, fromInvIndex) <- fromInventories.view.zipWithIndex
                          (fromStack, fromStackIndex) <- fromInv.toIterable.zipWithIndex if fromStack != null && passesFromFilter(fromInv, fromStackIndex, fromFilter)
                          (toInv, toInvIndex) <- toInventories.zipWithIndex
                          (toStack, toStackIndex) <- toInv.toIterable.zipWithIndex if (toStack == null || toStack.isItemEqual(fromStack) && toStack.stackSize < toStack.getMaxStackSize) && passesToFilter(fromStack, toInv, toStackIndex, toFilter)
                        } yield (fromInvIndex, fromStackIndex, toInvIndex, toStackIndex)

                        transfer.headOption.foreach{ case (fromInvIndex, fromStackIndex, toInvIndex, toStackIndex) =>
                          val fromStack = fromInventories(fromInvIndex).getStackInSlot(fromStackIndex)
                          val toStack = if(toInventories(toInvIndex).getStackInSlot(toStackIndex) == null) fromStack.splitStack(0) else toInventories(toInvIndex).getStackInSlot(toStackIndex)
                          val maxItems = toStack.getMaxStackSize - toStack.stackSize
                          val transferredItemCount = Math.min(maxItems, fromStack.stackSize)
                          fromStack.stackSize -= transferredItemCount
                          toStack.stackSize += transferredItemCount


                          toInventories(toInvIndex).setInventorySlotContents(toStackIndex, toStack)
                          if(fromStack.stackSize == 0){
                            fromInventories(fromInvIndex).setInventorySlotContents(fromStackIndex, null)
                          }
                        }
                      }
                    }
                  }
                }
                case other => throw new NativeFunctionException("can not transfer to " + other)
              }

            }
          }
      }
      case other => throw new NativeFunctionException("can not transfer from " + other)
    }
    itemTo
  }

  def passesFromFilter(inventory:IInventory, stackIndex:Int, filter:MMap[String, StackValue]):Boolean = {
    val stack = inventory.getStackInSlot(stackIndex)
    inventory match{
      case inventory:ISidedInventory => {
        val iSidedInventory = inventory.asInstanceOf[ISidedInventory]
        val validFacing = for{
          ListValue(sides) <- filter.get("sides")
          ListValue(items) <- filter.get("items") if items.isEmpty || items.exists(v => matchesItemFilter(stack, v))
          facing <- (if (sides.isEmpty) directions.values else for{StringValue(s) <- sides; facing <- directions.get(s)} yield facing).find{ facing =>
            inventory.getSlotsForFace(facing).contains(stackIndex) && inventory.canExtractItem(stackIndex, stack, facing)
          }
        } yield facing
        validFacing.isDefined
      }
      case inventory => (for{ListValue(items) <- filter.get("items")} yield items.isEmpty || items.exists(v => matchesItemFilter(stack, v))).getOrElse(false)
    }
  }

  def matchesItemFilter(stack:ItemStack, value:StackValue): Boolean ={
    (value match{
      case ObjectValue(mapping) => for{
        StringValue(mod) <- mapping.get("mod")
        StringValue(name) <- mapping.get("name")
      } yield {
          stack.getItem.equals(Item.getByNameOrId(s"$mod:$name"))
        }
      case other => None
    }).getOrElse(false)
  }

  def passesToFilter(fromStack:ItemStack, inventory:IInventory, stackIndex:Int, filter:MMap[String, StackValue]):Boolean = {
    inventory match{
      case inventory:ISidedInventory => {
        val validFacing = for {
          ListValue(sides) <- filter.get("sides")
          ListValue(items) <- filter.get("items") if items.isEmpty || items.exists(v => matchesItemFilter(fromStack, v))
          facing <- (if (sides.isEmpty) directions.values else for{StringValue(s) <- sides; facing <- directions.get(s)} yield facing).find{ facing =>
            inventory.getSlotsForFace(facing).contains(stackIndex) && inventory.canInsertItem(stackIndex, fromStack, facing)
          }
        } yield facing
        validFacing.isDefined
      }
      case _ => {
        (for{ListValue(items) <- filter.get("items")} yield items.isEmpty || items.exists(v => matchesItemFilter(fromStack, v))).getOrElse(false)
      }
    }
  }
}
