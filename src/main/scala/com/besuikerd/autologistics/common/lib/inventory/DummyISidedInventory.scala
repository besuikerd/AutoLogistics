package com.besuikerd.autologistics.common.lib.inventory

import com.besuikerd.autologistics.common.tile.traits.TileWrapper
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing

trait DummyISidedInventory extends DummyIInventory with ISidedInventory{
  override def inventory:ISidedInventory

  override def getSlotsForFace(side: EnumFacing): Array[Int] = inventory.getSlotsForFace(side)

  override def canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean = inventory.canExtractItem(index, stack, direction)

  override def canInsertItem(index: Int, itemStackIn: ItemStack, direction: EnumFacing): Boolean = inventory.canInsertItem(index, itemStackIn, direction)
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