package com.besuikerd.autologistics.common.item

import com.besuikerd.autologistics.common.ModItemNames
import com.besuikerd.autologistics.common.lib.util.ClipBoard
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{EnumAction, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util._
import net.minecraft.world.World

class ItemInventorySelector extends ItemMod(ModItemNames.InventorySelector){
  maxStackSize = 1

  final val KEY_COORDS = "coords"

  override def onItemUseFirst(stack: ItemStack, playerIn: EntityPlayer, world: World, pos: BlockPos, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    super.onItemUseFirst(stack, playerIn, world, pos, side, hitX, hitY, hitZ)
    if(stack.getTagCompound == null){
      stack.setTagCompound(new NBTTagCompound)
    }
    if(playerIn.isSneaking){
      val coords = stack.getTagCompound.getIntArray(KEY_COORDS)
      println(coords.length)
      if(coords.length == 3){
        val diff = pos.subtract(new Vec3i(coords(0), coords(1), coords(2)))
        val diffString = s"(${diff.getX}, ${diff.getY}, ${diff.getZ})"
        playerIn.addChatMessage(new ChatComponentText(s"Added $diffString to clipboard"))
        ClipBoard.set(diffString)
        stack.setTagCompound(null)
      } else{
        val coords = Array(pos.getX, pos.getY, pos.getZ)
        val coordString = s"${coords(0)}, ${coords(1)}, ${coords(2)}"
        playerIn.addChatMessage(new ChatComponentText(s"First position: " + coordString))
        stack.getTagCompound.setIntArray(KEY_COORDS, coords)
      }
    }
    true
  }
}
