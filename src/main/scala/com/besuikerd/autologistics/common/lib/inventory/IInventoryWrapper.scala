package com.besuikerd.autologistics.common.lib.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

trait IInventoryWrapper extends IInventory{
  def inventory:IInventory

  override def decrStackSize(index: Int, count: Int): ItemStack = inventory.decrStackSize(index, count)

  override def getSizeInventory: Int = inventory.getSizeInventory

  override def getInventoryStackLimit: Int = inventory.getInventoryStackLimit

  override def markDirty(): Unit = inventory.markDirty()

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = inventory.isItemValidForSlot(index, stack)

  override def getStackInSlotOnClosing(index: Int): ItemStack = inventory.getStackInSlotOnClosing(index)

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = inventory.setInventorySlotContents(index, stack)

  override def isUseableByPlayer(player: EntityPlayer): Boolean = inventory.isUseableByPlayer(player)

  override def getStackInSlot(index: Int): ItemStack = inventory.getStackInSlot(index)

  override def openInventory(): Unit = inventory.openInventory()

  override def closeInventory(): Unit = inventory.closeInventory()

  override def hasCustomInventoryName: Boolean = inventory.hasCustomInventoryName

  override def getInventoryName: String = inventory.getInventoryName
}
