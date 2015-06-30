package com.besuikerd.autologistics.common.lib

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

package object inventory {
  implicit class TraversableIInventory(val inventory:IInventory) extends Traversable[ItemStack]{
    override def foreach[U](f: (ItemStack) => U): Unit = {
      for(i <- 0 until inventory.getSizeInventory) f(inventory.getStackInSlot(i))
    }
  }
}
