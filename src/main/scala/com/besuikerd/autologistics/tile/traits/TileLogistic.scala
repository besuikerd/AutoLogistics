package com.besuikerd.autologistics.tile.traits

import com.besuikerd.autologistics.lib.dsl.vm._
import com.besuikerd.autologistics.tile.TileEntityMod
import com.besuikerd.autologistics.lib.inventory._
import net.minecraft.inventory.{ISidedInventory, IInventory}
import net.minecraft.item.{ItemStack, ItemBlock, Item}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{BlockPos, EnumFacing}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraftforge.oredict.OreDictionary
import scala.collection.mutable.{Map => MMap, ArrayBuffer}
import scala.collection.JavaConversions._

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
    values match{
      case List(mod:StringValue, name:StringValue, meta:NaturalNumber, _*) =>{
        ObjectValue(MMap(
          "filter" -> createEmptyFilter(),
          "mod" -> mod,
          "name" -> name,
          "meta" -> meta
        ))
      }
      case otherwise => throw NativeFunctionException(s"Wrong arguments for item filter")
    }
  }

  def createEmptyFilter() = ObjectValue(MMap(
    "sides" -> ListValue(),
    "items" -> ListValue()
  ))

  def nativeItemFilter(values:List[StackValue]): StackValue = {
    checkArgCount(values, 2)
    values match{
      case List(item:ObjectValue, filters:ListValue, _*)=> {
        val itemCopy = item.copy()
        if(itemCopy.mapping.get("filter").isEmpty){
          itemCopy.mapping.put("filter", createEmptyFilter())
        }
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

    val allInventories = findInventories
    for {
      (fromInventories, ObjectValue(fromFilter)) <- getInventories(allInventories, itemFrom) if fromInventories.nonEmpty
      (toInventories, ObjectValue(toFilter)) <- getInventories(allInventories, itemTo) if toInventories.nonEmpty
    } {
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
    itemTo
  }

  def getInventories(allInventories:IndexedSeq[TileEntity with IInventory], obj:StackValue):Option[(IndexedSeq[TileEntity with IInventory], ObjectValue)] = obj match{
    case ObjectValue(mapping) => {
      val byName = for{
        StringValue(mod) <- mapping.get("mod")
        StringValue(name) <- mapping.get("name")
        filter@ObjectValue(_) <- mapping.get("filter")
      } yield {
        val block = Block.getBlockFromName(s"$mod:$name")
        if(block == null){
          throw NativeFunctionException(s"Could not find block $mod:$name")
        }
        (allInventories.filter(_.getBlockType.equals(block)), filter)
      }

      byName.orElse{
        for{
          StringValue(tpe) <- mapping.get("type")
          NaturalNumber(x) <- mapping.get("x")
          NaturalNumber(y) <- mapping.get("y")
          NaturalNumber(z) <- mapping.get("z")
          filter@ObjectValue(_) <- mapping.get("filter").orElse{Some(createEmptyFilter())}
        } yield{
          val pos = if(tpe.equals("absolute")) new BlockPos(x,y,z) else getPos.add(x, y, z)
          (allInventories.filter(_.getPos.equals(pos)), filter)
        }
      }
    }
    case _ => None
  }

  def matchesMetadata(stack:ItemStack, meta:Int) = meta == -1 || stack.getMetadata == meta

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
        NaturalNumber(meta) <- mapping.get("meta")
      } yield {
          val itemMatch = if(mod.equals("ore")){

            println(OreDictionary.itemMatches(OreDictionary.getOres(name).get(0), stack, false))
            println(OreDictionary.getOres(name).iterator.exists {s => OreDictionary.itemMatches(s, stack, false)})
            OreDictionary.getOres(name).iterator.exists {s => OreDictionary.itemMatches(s, stack, false)}

          } else {
            stack.getItem.equals(Item.getByNameOrId(s"$mod:$name"))
          }
          itemMatch && matchesMetadata(stack, meta)
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
