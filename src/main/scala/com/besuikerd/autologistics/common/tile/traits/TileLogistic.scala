package com.besuikerd.autologistics.common.tile.traits

import java.util

import com.besuikerd.autologistics.common.lib.dsl.vm._
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.ObjectValue
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue._
import com.besuikerd.autologistics.common.tile.TileEntityMod
import com.besuikerd.autologistics.common.lib.inventory._
import net.minecraft.inventory.{InventoryCrafting, ISidedInventory, IInventory}
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.item.{ItemStack, ItemBlock, Item}
import net.minecraft.tileentity.TileEntity
import net.minecraft.block.Block
import net.minecraft.util.EnumFacing
import net.minecraftforge.oredict.OreDictionary
import scala.collection.mutable.{Map => MMap, ArrayBuffer}
import scala.collection.JavaConversions._
import com.besuikerd.autologistics.common.lib.collection.TraversableExtensions.TraversableExtensions
import com.besuikerd.autologistics.common.lib.collection.MMapExtensions.MMapExtensions

trait TileLogistic extends TileEntityMod{
  this: TileEntityMod
    with TileCable
    with TileVirtualMachine =>

//  virtualMachine.addNative("_itemFilter", nativeItemFilter)
//  virtualMachine.addNative("_transferTo", nativeTransferTo)

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

  directions.keys.foreach(dir => virtualMachine.addGlobal(dir, new StringValue(dir)))

  /*

  def createEmptyFilter(): ObjectValue = {
    val obj = new ObjectValue()
    obj.mapping.put("sides", new ListValue())
    obj.mapping.put("items", new ListValue())
    obj
  }

  val nativeItemFilter: ScalaNativeFunction = { (vm, values) =>
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
          val itemFilters = filters.list.collect{case o@ObjectValue(mapping) if mapping.get("type").exists{case StringValue(tpe) => tpe.equals("item"); case _ => false} => o}
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


    (itemFrom, itemTo) match {
      case (oLeft@ObjectValue(left), oRight@ObjectValue(right)) if isMoveItemsType(left) && isMoveItemsType(right) => {
        moveItems(List(oLeft), List(oRight))
        oRight
      }
      case (oLeft@ObjectValue(left), lRight@ListValue(right)) if isMoveItemsType(left) && isCraftingRecipe(right) => ??? //craftFrom(oLeft, lRight)
      case (oLeft@ObjectValue(left), oRight@ObjectValue(right)) if isCraftingInputResultType(left) && isMoveItemsType(right) => ??? //craftTo(oLeft, oRight)
      case _ => itemTo
    }
  }

  def isMoveItemsType(mapping:MMap[String, StackValue]) = mapping.contains("type") || mapping.contains("mod") && mapping.contains("name")
  def isCraftingRecipe(list:ArrayBuffer[StackValue]) = {println("isCrafting"); list.length <= 3 && list.forall {
    case ListValue(sublist) => sublist.length <= 3
    case _ => false
  }}
  def isCraftingInputResultType(mapping:MMap[String, StackValue]) = mapping.contains("recipe") && mapping.contains("input")



  def getValidInventories[A <: Traversable[TileEntity with IInventory]](inventories:A, filters:Traversable[StackValue]): A = {
    inventories.filter{ inventory =>
      filters.exists{filter =>
        filterByName(inventory, filter) ||
        filterByPosition(inventory, filter)
      }
    }.asInstanceOf[A]
  }

  def filterByName(inventory:TileEntity with IInventory, filter:StackValue):Boolean = {
    (for{
      ObjectValue(mapping) <- Some(filter)
      StringValue(mod) <- mapping.get("mod")
      StringValue(name) <- mapping.get("name")
    } yield {
      val block = Block.getBlockFromName(s"$mod:$name")
      val correctMeta = mapping.get("meta") match {
        case Some(NaturalNumber(n)) => inventory.getBlockMetadata == n
        case _ => true
      }
      correctMeta && inventory.getBlockType.equals(block)
    }).getOrElse(false)
  }

  def filterByPosition(inventory:TileEntity with IInventory, filter:StackValue):Boolean = {
    (for{
      ObjectValue(mapping) <- Some(filter)
      StringValue(tpe) <- mapping.get("type")
      NaturalNumber(x) <- mapping.get("x")
      NaturalNumber(y) <- mapping.get("y")
      NaturalNumber(z) <- mapping.get("z")
      filter@ObjectValue(_) <- mapping.get("filter").orElse{Some(createEmptyFilter())}
    } yield {
      val pos = if (tpe.equals("absolute")) new BlockPos(x, y, z) else getPos.add(x, y, z)
      inventory.getPos.equals(pos)
    }).getOrElse(false)
  }

  def getValidInputSlots(inventory: TileEntity with IInventory, filters:List[StackValue]):List[(Int, Int)] = {
    val diffFunction = (count:Int, limit:Int) => count - limit
    (0 until inventory.getSizeInventory).toList.flatMap{ stackIndex =>
      val stack = inventory.getStackInSlot(stackIndex)
      if(stack == null) List.empty else {
        val optAmount = filters.collectFirst { case ObjectValue(item) =>
          val filter = item.getOfType[ObjectValue]("filter").getOrElse(createEmptyFilter()).mapping

          inventory match {
            case inventory: ISidedInventory => {
              for {
                ListValue(sides) <- filter.get("sides")
                ListValue(items) <- filter.get("items") if items.isEmpty || items.exists(v => matchesItemFilter(stack, v))
                facing <- (if (sides.isEmpty) directions.values else for {StringValue(s) <- sides; facing <- directions.get(s)} yield facing).find { facing =>
                  inventory.getSlotsForFace(facing).contains(stackIndex) && inventory.canExtractItem(stackIndex, stack, facing)
                }
                amount <- (if (sides.isEmpty) directions.values else for {StringValue(s) <- sides; facing <- directions.get(s)} yield facing).view.map { facing =>
                  val faceSlots = inventory.getSlotsForFace(facing)
                  if (faceSlots.contains(stackIndex) && inventory.canExtractItem(stackIndex, stack, facing)) {
                    passesAmount(stack, filter, faceSlots, inventory, diffFunction)
                  } else 0
                }.find(_ > 0)
              } yield amount
            }
            case _ => for {
              ListValue(items) <- filter.get("items")
              amount <- Some(passesAmount(stack, filter, (0 until inventory.getSizeInventory).toArray, inventory, diffFunction)) if (items.isEmpty || items.exists(v => matchesItemFilter(stack, v))) && amount > 0
            } yield amount
          }
        }
        optAmount.flatten.map(amount => List((stackIndex, Math.min(stack.stackSize, amount)))).getOrElse(List.empty)
      }
    }
  }

  def getValidOutputSlots(inStack:ItemStack, inventory: TileEntity with IInventory, filters:List[StackValue]):List[(Int, Int)] = {
    val diffFunction =(count:Int, limit:Int) => limit - count
    (0 until inventory.getSizeInventory).toList.flatMap { stackIndex =>
      val outStack = inventory.getStackInSlot(stackIndex)
      val optAmount = filters.collectFirst{ case ObjectValue(item) =>
        val filter = item.getOfType[ObjectValue]("filter").getOrElse(createEmptyFilter()).mapping
        inventory match{
          case inventory:ISidedInventory => {
            for {
              ListValue(sides) <- filter.get("sides")
              ListValue(items) <- filter.get("items") if items.isEmpty || items.exists(v => matchesItemFilter(inStack, v))
              amount <- (if (sides.isEmpty) directions.values else for{StringValue(s) <- sides; facing <- directions.get(s)} yield facing).view.map{ facing =>
                val faceSlots = inventory.getSlotsForFace(facing)
                if(faceSlots.contains(stackIndex) && inventory.canInsertItem(stackIndex, inStack, facing)){
                  passesAmount(inStack, filter, faceSlots, inventory, diffFunction)
                } else 0
              }.find(_ > 0)
            } yield amount
          }
          case _ => {
            for{
              ListValue(items) <- filter.get("items")
              amount <- Some(passesAmount(inStack, filter, (0 until inventory.getSizeInventory).toArray, inventory, diffFunction)) if (items.isEmpty || items.exists(v => matchesItemFilter(inStack, v))) && amount > 0
            } yield amount
          }
        }
      }
      optAmount.flatten.map(amount => List((stackIndex, amount))).getOrElse(List.empty)
    }
  }

//  val diffFunction =(count:Int, limit:Int) => limit - count


  case class ItemTransfer(
    in:TileEntity with IInventory,
    inSlotIndex:Int,
    inAmount:Int,
    out:TileEntity with IInventory,
    outSlotIndex:Int,
    outAmount: Int
  )

  def moveItems(from:List[StackValue], to:List[StackValue]): Unit = {
    val allInventories = findInventories

    val itemsPerOperation = 64 //TODO define somewhere else

    val inSlots = for {
      inInventory <- getValidInventories(allInventories, from).view
      (inSlotIndex, inAmount) <- getValidInputSlots(inInventory, from)
    } yield (inInventory, inSlotIndex, inAmount)

    val outInventories = getValidInventories(allInventories, to)

    inSlots.countWhile[Int](_ < itemsPerOperation) { case (in, inSlotIndex, inAmount) =>
      val stackIn = in.getStackInSlot(inSlotIndex)
      val outSlots = for{
        outInventory <- outInventories.view
        (outSlotIndex, outAmount) <- getValidOutputSlots(stackIn, outInventory, to)
      } yield (outInventory, outSlotIndex, outAmount)

      outSlots.foldWhile(0)(_ < inAmount){ case (count, (out, outSlotIndex, outAmount)) =>
        val stackOut = out.getStackInSlot(outSlotIndex)
        val amountLeft = itemsPerOperation - count

        if(stackOut == null){
          val transferAmount = List(inAmount - count, amountLeft, outAmount).min
          val split = in.decrStackSize(inSlotIndex, transferAmount)
//          println("empty stack: " + (transferAmount, inAmount, amountLeft, outAmount))
          out.setInventorySlotContents(outSlotIndex, split)
          count + transferAmount
        } else {
          val transferAmount = List(inAmount - count, outAmount, stackOut.getMaxStackSize - stackOut.stackSize).min
//          println("nonempty stack: " + (transferAmount, inAmount, amountLeft, outAmount))
          in.decrStackSize(inSlotIndex, transferAmount)
          out.decrStackSize(outSlotIndex, -transferAmount)
          count + transferAmount
        }
      }._1
    }
  }
  */

  /*
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
  */

  /*
  def craftFrom (left:ObjectValue, right:ListValue):StackValue = {
      ObjectValue(MMap[String, StackValue]() ++ Map(
        "input" -> left,
        "recipe" -> right
      ))
  }

  def craftTo: (ObjectValue, ObjectValue) => StackValue = { case (ObjectValue(left), output) =>
    println("craftTo")
    for{
      input @ ObjectValue(_) <- left.get("input")
      ListValue(recipe) <- left.get("recipe")
      allInventories <- Some(findInventories)
      (fromInventories, ObjectValue(fromFilter)) <-  getInventories(allInventories, input)
      fakeFromInventories <- Some(fromInventories.map{
        case t:TileEntity with ISidedInventory => DummyISidedInventory(t,t)
        case t => DummyIInventory(t,t)
      })
    } yield {

      val slots = for{
        (ListValue(row), i) <- recipe.zipWithIndex
        (item@ObjectValue(mapping), j) <- row.zipWithIndex
      } yield {
          val craftSlot = i * 3 + j
          for{
            found <- (for{
              (fromInv, fromInvIndex) <- fakeFromInventories.view.zipWithIndex
              (fromStack, fromStackIndex) <- fromInv.toIterable.zipWithIndex if fromStack != null && matchesItemFilter(fromStack, item)
              fromLimit <- passesFromFilter(fromInv, fromStackIndex, fromFilter).toList if fromLimit > 0
            } yield{
                val stack = fromInv.decrStackSize(fromStackIndex, 1)
                (stack, craftSlot, fromInvIndex, fromStackIndex)
            }).headOption
          } yield found
      }

      if(slots.forall(_.isDefined)){
        println("ingredients found")
        val inventoryCrafting = new InventoryCrafting(new DummyContainer, 3, 3)
        for{
          Some(tuple@(stack, craftSlot, invIndex, stackIndex)) <- slots
        }{
          println(tuple)
          inventoryCrafting.setInventorySlotContents(craftSlot, stack)
        }
        val craftResult = CraftingManager.getInstance().findMatchingRecipe(inventoryCrafting, getWorld)
        println(craftResult)

        if(craftResult != null){
          val remainder = CraftingManager.getInstance().func_180303_b(inventoryCrafting, getWorld).filter(_ != null)
          val toItems = craftResult +: remainder
          println(toItems.mkString("[", ",", "]"))

          val toSlots = for{
//            (toInv, toInvIndex) <- getInventories(fakeInventories.view.zipWithIndex)
            ObjectValue(outFilter) <- output.mapping.get("filter")
            (toInventories, toFilter) <- getInventories(allInventories, output)
            fakeToInventories <- Some(fromInventories.map(fakeInventory))
            item <- toItems
            (toInv, toInvIndex) <- fakeToInventories.zipWithIndex
            (toStack, toStackIndex) <- toInv.toIterable.zipWithIndex if toStack == null || toStack.isItemEqual(item) && matchesItemFilter(item, output)
            toLimit <- passesToFilter(toStack, toInv, toStackIndex, outFilter) if toLimit > 0
          } {
            if(toStack == null){
              toInv.setInventorySlotContents(toInvIndex, toStack)
              println((toInvIndex, toInv.getStackInSlot(toInvIndex)))
            }
          }

        }
      }

    }
    output
  }

  def fakeInventory(inventory:TileEntity with IInventory) = inventory match {
    case t:TileEntity with ISidedInventory => DummyISidedInventory(t,t)
    case t => DummyIInventory(t,t)
  }
  */


  /*
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
      } yield {
          val itemMatch = if(mod.equals("ore")){
            OreDictionary.getOres(name).iterator.exists {s => OreDictionary.itemMatches(s, stack, false)}
          } else {
            stack.getItem.equals(Item.getByNameOrId(s"$mod:$name"))
          }
          val validmeta = mapping.get("meta") match{
            case Some(NaturalNumber(n)) => matchesMetadata(stack, n)
            case None => true
            case _ => false
          }

          itemMatch && validmeta
        }
      case other => None
    }).getOrElse(false)
  }

  def passesToFilter(toStack:ItemStack, inventory:IInventory, stackIndex:Int, filter:MMap[String, StackValue]):Option[Int] = {
    val diffFunction =(count:Int, limit:Int) => limit - count
    inventory match{
      case inventory:ISidedInventory => {
        for {
          ListValue(sides) <- filter.get("sides")
          ListValue(items) <- filter.get("items") if items.isEmpty || items.exists(v => matchesItemFilter(toStack, v))
          amount <- (if (sides.isEmpty) directions.values else for{StringValue(s) <- sides; facing <- directions.get(s)} yield facing).view.map{ facing =>
            val faceSlots = inventory.getSlotsForFace(facing)
            if(faceSlots.contains(stackIndex) && inventory.canInsertItem(stackIndex, toStack, facing)){
              passesAmount(toStack, filter, faceSlots, inventory, diffFunction)
            } else 0
          }.find(_ > 0)
        } yield amount
      }
      case _ => {
        for{
          ListValue(items) <- filter.get("items")
          amount <- Some(passesAmount(toStack, filter, (0 until inventory.getSizeInventory).toArray, inventory, diffFunction)) if (items.isEmpty || items.exists(v => matchesItemFilter(toStack, v))) && amount > 0
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
    optPass.map(n => Math.min(n, stack.stackSize)).getOrElse(stack.stackSize)
  }

  */
}
