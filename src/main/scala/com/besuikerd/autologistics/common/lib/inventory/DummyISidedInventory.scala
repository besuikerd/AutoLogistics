package com.besuikerd.autologistics.common.lib.inventory

import com.besuikerd.autologistics.common.tile.traits.TileWrapper
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing

trait DummyISidedInventory extends DummyIInventory with ISidedInventory{
  override def inventory:ISidedInventory

  override def canExtractItem(index : Int, stack : ItemStack, direction : Int): Boolean = inventory.canExtractItem(index, stack, direction)

  override def canInsertItem(index : Int, stack : ItemStack, direction : Int): Boolean = inventory.canInsertItem(index, stack, direction)

  override def getAccessibleSlotsFromSide(side : Int): Array[Int] = inventory.getAccessibleSlotsFromSide(side)
}

object DummyISidedInventory{
  def apply[A <: TileEntity](theTile:A, theInventory:ISidedInventory):TileEntity with DummyISidedInventory = {
    new TileEntity with TileWrapper with DummyISidedInventory{
      override def tile: A = theTile
      override def inventory: ISidedInventory = theInventory
      override def markDirty() = tile.markDirty()
    }
  }
}