package com.besuikerd.autologistics.tile.traits

import com.besuikerd.autologistics.lib.dsl.vm._
import com.besuikerd.autologistics.tile.TileEntityMod
import com.besuikerd.autologistics.lib.inventory._
import net.minecraft.inventory.IInventory
import net.minecraft.item.{ItemStack, ItemBlock, Item}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import scala.collection.mutable.{Map => MMap}

trait TileLogistic extends TileEntityMod{
  this: TileEntityMod
    with TileCable
    with TileVirtualMachine =>

  virtualMachine.addNative("_getItem", nativeGetItem)
  virtualMachine.addNative("_itemFilter", nativeItemFilter)
  virtualMachine.addNative("_transferTo", nativeTransferTo)

  val directions = Set("north", "east", "south", "west", "up", "down")
  directions.foreach(dir => virtualMachine.globals.put(dir, StringValue(dir)))

  def nativeGetItem(values:List[StackValue]): StackValue = {
    checkArgCount(values, 2)
    val (mod :: xs) = values
    val (name :: _) = xs
    ObjectValue(MMap(
      "filter" -> ObjectValue(),
      "mod" -> mod,
      "name" -> name
    ))
  }

  def nativeItemFilter(values:List[StackValue]): StackValue = {
    checkArgCount(values, 3)
    val (item :: xs) = values
    val (kv :: args) = xs


    item match{
      case obj:ObjectValue => {
        val itemCopy = obj.copy()
        itemCopy.mapping.get("filter") match{
          case Some(ObjectValue(filters)) => {
            filters ++= kv.asInstanceOf[ObjectValue].mapping
            args.collect {
              case value@StringValue(s) if directions.contains(s) => filters.put("direction", value)
              case value@ObjectValue(filterArgs) if filterArgs.contains("item") => filters.put("item", value)
              case value: RealNumber => filters.put("amount", value)
            }
          }
          case Some(other) => throw new NativeFunctionException("can not filter " + other.stringRepresentation)
          case None => throw new NativeFunctionException("can not filter object without filter field")
        }
        itemCopy
      }
      case other => throw new NativeFunctionException("can not filter " + other.stringRepresentation)
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
                          (toStack, toStackIndex) <- toInv.toIterable.zipWithIndex if toStack == null || toStack.isItemEqual(fromStack) && toStack.stackSize < toStack.getMaxStackSize
                        } yield (fromInvIndex, fromStackIndex, toInvIndex, toStackIndex)

                        transfer.headOption.foreach{ case (fromInvIndex, fromStackIndex, toInvIndex, toStackIndex) =>
                          val fromStack = fromInventories(fromInvIndex).getStackInSlot(fromStackIndex)
                          val toStack = if(toInventories(toInvIndex).getStackInSlot(toStackIndex) == null) new ItemStack(fromStack.getItem, 0) else toInventories(toInvIndex).getStackInSlot(toStackIndex)
                          val maxItems = toStack.getMaxStackSize - toStack.stackSize
                          val transferredItemCount = Math.min(maxItems, fromStack.stackSize)
                          fromStack.stackSize -= transferredItemCount
                          toStack.stackSize += transferredItemCount
                          toInventories(toInvIndex).setInventorySlotContents(toStackIndex, toStack)
                          if(fromStack.stackSize == 0){
                            fromInventories(fromInvIndex).setInventorySlotContents(fromStackIndex, null)
                          }
                        }

                        /*
                        val fromSlots = for{
                          (inventory, i) <- fromInventories.zipWithIndex
                          (slot, j) <- inventory.toList.zipWithIndex if slot != null //&& GameRegistry.findUniqueIdentifierFor(slot.getItem).toString.equals(s"$mod:$name")
                        } yield (i, j)

                        val toSlots = for{
                          (inventory, i) <- toInventories.zipWithIndex
                          fromSlot <- fromSlots.map{case (i,j) => fromInventories(i).getStackInSlot(j)}
                          (slot, j) <- inventory.toList.zipWithIndex if slot == null || slot.isItemEqual(fromSlot) && slot.getMaxStackSize < slot.stackSize
                        } yield (i,j)
                        println(toSlots)

                        if(fromSlots.nonEmpty && toSlots.nonEmpty){
                          val (fromInvIndex, fromSlotIndex) = fromSlots.head
                          val fromStack = fromInventories(fromInvIndex).getStackInSlot(fromSlotIndex)
                          val (toInvIndex, toSlotIndex) = toSlots.head
                          val toStack = if(toInventories(toInvIndex).getStackInSlot(toSlotIndex) == null) new ItemStack(fromStack.getItem, 0) else toInventories(toInvIndex).getStackInSlot(toSlotIndex)
                          val maxItems = toStack.getMaxStackSize - toStack.stackSize

                          val transferredItemCount = Math.min(maxItems, fromStack.stackSize)
                          fromStack.stackSize -= transferredItemCount
                          toStack.stackSize += transferredItemCount
                          toInventories(toInvIndex).setInventorySlotContents(toSlotIndex, toStack)
                          if(fromStack.stackSize == 0){
                            fromInventories(fromInvIndex).setInventorySlotContents(fromSlotIndex, null)
                          }
                        }
                        */
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



    true
  }

  def passesToFilter(inventory:IInventory, stackIndex:Int, filter:MMap[String, StackValue]):Boolean = {
    true
  }
}
