package com.besuikerd.autologistics.common.lib.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container

class DummyContainer extends Container {
  override def canInteractWith(playerIn: EntityPlayer): Boolean = false
}