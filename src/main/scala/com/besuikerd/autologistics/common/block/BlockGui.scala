package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.{AutoLogistics, GuiRegistry}
import com.besuikerd.autologistics.client.lib.gui.IGuiEntry
import com.besuikerd.autologistics.common.ModConstants
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

trait BlockGui extends Block{
  def guiEntry:IGuiEntry

  abstract override def onBlockActivated(world : World, x : Int, y : Int, z : Int, player : EntityPlayer, side : Int, hitX : Float, hitY : Float, hitZ : Float): Boolean = {
    super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ)
    player.openGui(AutoLogistics, GuiRegistry.instance.getGuiId(guiEntry.getName), world, x, y, z)
    return true;
  }
}
