package com.besuikerd.autologistics.common.item

import com.besuikerd.autologistics.common.ModItemNames
import com.besuikerd.autologistics.common.lib.util.ClipBoard
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util._
import net.minecraft.world.World

class ItemInventorySelector extends ItemMod(ModItemNames.InventorySelector){
  maxStackSize = 1
  setTextureName("compass")

  final val KEY_COORDS = "coords"


  override def onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ)
    if(stack.getTagCompound == null){
      stack.setTagCompound(new NBTTagCompound)
    }
    if(player.isSneaking){
      val coords = stack.getTagCompound.getIntArray(KEY_COORDS)
      println(coords.length)
      if(coords.length == 3){
        val diffString = s"(${coords(0) - x}, ${coords(1) - y}, ${coords(2) - z})"
        player.addChatMessage(new ChatComponentText(s"Added $diffString to clipboard"))
        ClipBoard.set(diffString)
        stack.setTagCompound(null)
      } else{
        val coords = Array(x, y, z)
        val coordString = s"${coords(0)}, ${coords(1)}, ${coords(2)}"
        player.addChatMessage(new ChatComponentText(s"First position: " + coordString))
        stack.getTagCompound.setIntArray(KEY_COORDS, coords)
      }
    }
    true
  }
}
