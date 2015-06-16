package com.besuikerd.autologistics.common.lib.inventory

import com.besuikerd.autologistics.common.tile.traits.TileWrapper
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity

trait DummyIInventory extends IInventoryWrapper{
  private val slots = new Array[ItemStack](inventory.getSizeInventory)

  override def decrStackSize(index: Int, count: Int): ItemStack = {
    val stack = getStackInSlot(index)
    stack.splitStack(1)
  }

  override def getStackInSlot(index: Int): ItemStack = {
    if (slots(index) == null && inventory.getStackInSlot(index) != null) {
      slots(index) = inventory.getStackInSlot(index).copy()
    }
    val stack = slots(index)
    if(stack != null && stack.stackSize <= 0) {println(stack.stackSize); null} else stack
  }

  override def getStackInSlotOnClosing(index: Int): ItemStack = getStackInSlot(index)
}

object DummyIInventory{
  def apply[A <: TileEntity](theTile:A, theInventory:IInventory):TileEntity with DummyIInventory = {
    new TileEntity with TileWrapper with DummyIInventory{
      override def tile: A = theTile
      override def inventory: IInventory = theInventory

      override def markDirty() = tile.markDirty()
    }
  }
}