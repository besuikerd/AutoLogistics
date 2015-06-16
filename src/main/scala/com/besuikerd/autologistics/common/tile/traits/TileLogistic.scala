package com.besuikerd.autologistics.common.tile.traits

import com.besuikerd.autologistics.common.lib.dsl.vm._
import com.besuikerd.autologistics.common.tile.TileEntityMod
import com.besuikerd.autologistics.common.lib.inventory._
import net.minecraft.inventory.{InventoryCrafting, ISidedInventory, IInventory}
import net.minecraft.item.crafting.CraftingManager
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
    "top" -> EnumFacing.UP,
    "down" -> EnumFacing.DOWN,
    "bottom" -> EnumFacing.DOWN
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
          filters.list.collectFirst{case n@NaturalNumber(_) => n}.foreach(filterMapping.put("amount", _))
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

    println(itemTo)

    (itemFrom, itemTo) match {
      case (oLeft@ObjectValue(left), oRight@ObjectValue(right)) if isMoveItemsType(left) && isMoveItemsType(right) => moveItems(oLeft, oRight)
      case (oLeft@ObjectValue(left), lRight@ListValue(right)) if isMoveItemsType(left) && isCraftingRecipe(right) => craftFrom(oLeft, lRight)
      case (oLeft@ObjectValue(left), oRight@ObjectValue(right)) if isCraftingInputResultType(left) && isMoveItemsType(right) => craftTo(oLeft, oRight)
      case _ => itemTo
    }
  }

  def isMoveItemsType(mapping:MMap[String, StackValue]) = mapping.contains("type") || mapping.contains("mod") && mapping.contains("name")
  def isCraftingRecipe(list:ArrayBuffer[StackValue]) = {println("isCrafting"); list.length <= 3 && list.forall {
    case ListValue(sublist) => sublist.length <= 3
    case _ => false
  }}
  def isCraftingInputResultType(mapping:MMap[String, StackValue]) = mapping.contains("recipe") && mapping.contains("input")

  def craftFrom (left:ObjectValue, right:ListValue):StackValue = {
      ObjectValue(MMap[String, StackValue]() ++ Map(
        "input" -> left,
        "recipe" -> right
      ))
  }

  def craftTo: (ObjectValue, ObjectValue) => StackValue = { case (ObjectValue(left), oRight@ObjectValue(right)) =>
    println("craftTo")
    for{
      input @ ObjectValue(_) <- left.get("input")
      ListValue(recipe) <- left.get("recipe")
      allInventories <- Some(findInventories)
      (fromInventories, ObjectValue(fromFilter)) <-  getInventories(allInventories, input)
      fakeInventories <- Some(fromInventories.map{
        case t:TileEntity with ISidedInventory => DummyISidedInventory(t,t)
        case t => DummyIInventory(t,t)
      })
    } yield {

      val slots = for{
        (ListValue(row), i) <- recipe.zipWithIndex
        (item@ObjectValue(mapping), j) <- row.zipWithIndex
        ObjectValue(craftSlotFilter) <- mapping.get("filter")
      } yield {
          val craftSlot = i * 3 + j
          val allInventories = findInventories
          for{
            found <- (for{
              (fromInv, fromInvIndex) <- fromInventories.view.zipWithIndex
              (fromStack, fromStackIndex) <- fromInv.toIterable.zipWithIndex if fromStack != null
              fromLimit <- passesFromFilter(fromInv, fromStackIndex, fromFilter).toList if fromLimit > 0
              craftLimit <- passesFromFilter(fromInv, fromStackIndex, craftSlotFilter) if craftLimit > 0
            } yield{
                fromInventories(fromInvIndex).decrStackSize(fromStackIndex, 1)
                (craftSlot, fromInvIndex, fromStackIndex)
            }).headOption
          } yield found
      }

      if(slots.forall(_.isDefined)){
        println("ingredients found")
        val inventoryCrafting = new InventoryCrafting(new DummyContainer, 3, 3)
        for{
          Some(tuple@(craftSlot, invIndex, stackIndex)) <- slots
        }{
          val stack = fromInventories(invIndex).getStackInSlot(stackIndex)
          println(tuple)

          inventoryCrafting.setInventorySlotContents(craftSlot, stack)
        }
        val craftResult = CraftingManager.getInstance().findMatchingRecipe(inventoryCrafting, getWorld)
        println(craftResult)

        if(craftResult != null){

        }
      }

    }
    oRight
  }

  def moveItems(itemFrom:ObjectValue, itemTo:ObjectValue): StackValue = {
    val allInventories = findInventories
    for {
      (fromInventories, ObjectValue(fromFilter)) <- getInventories(allInventories, itemFrom) if fromInventories.nonEmpty
      (toInventories, ObjectValue(toFilter)) <- getInventories(allInventories, itemTo) if toInventories.nonEmpty
    } {
      val transfer = for{
        (fromInv, fromInvIndex) <- fromInventories.view.zipWithIndex
        (fromStack, fromStackIndex) <- fromInv.toIterable.zipWithIndex if fromStack != null
        fromLimit <- passesFromFilter(fromInv, fromStackIndex, fromFilter).toList
        (toInv, toInvIndex) <- toInventories.zipWithIndex
        (toStack, toStackIndex) <- toInv.toIterable.zipWithIndex if toStack == null || toStack.isItemEqual(fromStack) && toStack.stackSize < toStack.getMaxStackSize
        toLimit <- passesToFilter(fromStack, toInv, toStackIndex, toFilter).toList
      } yield (fromInvIndex, fromStackIndex, fromLimit, toInvIndex, toStackIndex, toLimit)

      transfer.headOption.foreach{ case (fromInvIndex, fromStackIndex, fromLimit, toInvIndex, toStackIndex, toLimit) =>
        val fromStack = fromInventories(fromInvIndex).getStackInSlot(fromStackIndex)
        val toStack = if(toInventories(toInvIndex).getStackInSlot(toStackIndex) == null) fromStack.splitStack(0) else toInventories(toInvIndex).getStackInSlot(toStackIndex)
        val maxItems = toStack.getMaxStackSize - toStack.stackSize
        val transferredItemCount = Array(maxItems, fromStack.stackSize, toLimit, fromLimit).min
        if(transferredItemCount > 0){
          fromStack.stackSize -= transferredItemCount
          toStack.stackSize += transferredItemCount
          toInventories(toInvIndex).setInventorySlotContents(toStackIndex, toStack)
          if(fromStack.stackSize == 0){
            fromInventories(fromInvIndex).setInventorySlotContents(fromStackIndex, null)
          }
          fromInventories(fromInvIndex).markDirty()
          toInventories(toInvIndex).markDirty()
        }
      }
    }
    itemTo
  }


  def getInventories(allInventories:IndexedSeq[TileEntity with IInventory], item:StackValue):Option[(IndexedSeq[TileEntity with IInventory], ObjectValue)] = item match{
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

          println(name)
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

  def passesFromFilter(inventory:IInventory, stackIndex:Int, filter:MMap[String, StackValue]):Option[Int] = {
    val diffFunction = (count:Int, limit:Int) => count - limit
    val stack = inventory.getStackInSlot(stackIndex)
    inventory match{
      case inventory:ISidedInventory => {
        for{
          ListValue(sides) <- filter.get("sides")
          ListValue(items) <- filter.get("items") if items.isEmpty || items.exists(v => matchesItemFilter(stack, v))
          facing <- (if (sides.isEmpty) directions.values else for{StringValue(s) <- sides; facing <- directions.get(s)} yield facing).find{ facing =>
            inventory.getSlotsForFace(facing).contains(stackIndex) && inventory.canExtractItem(stackIndex, stack, facing)
          }
          amount <- (if (sides.isEmpty) directions.values else for{StringValue(s) <- sides; facing <- directions.get(s)} yield facing).view.map{ facing =>
            val faceSlots = inventory.getSlotsForFace(facing)
            if(faceSlots.contains(stackIndex) && inventory.canExtractItem(stackIndex, stack, facing)){
              passesAmount(stack, filter, faceSlots, inventory, diffFunction)
            } else 0
          }.find(_ > 0)
        } yield amount
      }
      case _ => for{
        ListValue(items) <- filter.get("items")
        amount <- Some(passesAmount(stack, filter, (0 until inventory.getSizeInventory).toArray, inventory, diffFunction)) if (items.isEmpty || items.exists(v => matchesItemFilter(stack, v))) && amount > 0
      } yield amount
        //(for{ListValue(items) <- filter.get("items")} yield items.isEmpty || items.exists(v => matchesItemFilter(stack, v))).getOrElse(false)
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
            OreDictionary.getOres(name).iterator.exists {s => OreDictionary.itemMatches(s, stack, false)}

          } else {
            stack.getItem.equals(Item.getByNameOrId(s"$mod:$name"))
          }
          itemMatch && matchesMetadata(stack, meta)
        }
      case other => None
    }).getOrElse(false)
  }

  def passesToFilter(fromStack:ItemStack, inventory:IInventory, stackIndex:Int, filter:MMap[String, StackValue]):Option[Int] = {
    val diffFunction =(count:Int, limit:Int) => limit - count
    inventory match{
      case inventory:ISidedInventory => {
        for {
          ListValue(sides) <- filter.get("sides")
          ListValue(items) <- filter.get("items") if items.isEmpty || items.exists(v => matchesItemFilter(fromStack, v))
          amount <- (if (sides.isEmpty) directions.values else for{StringValue(s) <- sides; facing <- directions.get(s)} yield facing).view.map{ facing =>
            val faceSlots = inventory.getSlotsForFace(facing)
            if(faceSlots.contains(stackIndex) && inventory.canInsertItem(stackIndex, fromStack, facing)){
              passesAmount(fromStack, filter, faceSlots, inventory, diffFunction)
            } else 0
          }.find(_ > 0)
        } yield amount
      }
      case _ => {
        for{
          ListValue(items) <- filter.get("items")
          amount <- Some(passesAmount(fromStack, filter, (0 until inventory.getSizeInventory).toArray, inventory, diffFunction)) if (items.isEmpty || items.exists(v => matchesItemFilter(fromStack, v))) && amount > 0
        } yield amount
      }
    }
  }

  def passesAmount(stack:ItemStack, filter:MMap[String, StackValue], slots:Array[Int], inventory: IInventory, diffFunction: (Int, Int) => Int): Int = {
    val optPass = for{
      NaturalNumber(amount) <- filter.get("amount")
      ListValue(items) <- filter.get("items")
    } yield {
        val count = slots.map(inventory.getStackInSlot).filter(s => s != null && (items.isEmpty || items.exists(i => matchesItemFilter(s, i)))).foldRight(0)(_.stackSize + _)
        diffFunction(count, amount)
      }
    optPass.getOrElse(stack.getMaxStackSize)
  }
}
