package com.besuikerd.autologistics.common.lib.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.IChatComponent

trait IInventoryWrapper extends IInventory{
  val inventory:IInventory

  override def closeInventory(player: EntityPlayer): Unit = inventory.closeInventory(player)

  override def decrStackSize(index: Int, count: Int): ItemStack = inventory.decrStackSize(index, count)

  override def getSizeInventory: Int = inventory.getSizeInventory

  override def getInventoryStackLimit: Int = inventory.getInventoryStackLimit

  override def clear(): Unit = inventory.clear()

  override def markDirty(): Unit = inventory.markDirty()

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = inventory.isItemValidForSlot(index, stack)

  override def getStackInSlotOnClosing(index: Int): ItemStack = inventory.getStackInSlotOnClosing(index)

  override def openInventory(player: EntityPlayer): Unit = inventory.openInventory(player)

  override def getFieldCount: Int = inventory.getFieldCount

  override def getField(id: Int): Int = inventory.getField(id)

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = inventory.setInventorySlotContents(index, stack)

  override def isUseableByPlayer(player: EntityPlayer): Boolean = inventory.isUseableByPlayer(player)

  override def getStackInSlot(index: Int): ItemStack = inventory.getStackInSlot(index)

  override def setField(id: Int, value: Int): Unit = inventory.setField(id, value)

  override def getDisplayName: IChatComponent = inventory.getDisplayName

  override def getName: String = inventory.getName

  override def hasCustomName: Boolean = inventory.hasCustomName
}
