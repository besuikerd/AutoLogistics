package com.besuikerd.autologistics.common.item

import com.besuikerd.autologistics.common.ModItemNames
import com.besuikerd.autologistics.common.lib.util.ClipBoard
import com.besuikerd.autologistics.common.tile.logistic.filter.LogisticFilterName
import com.google.common.collect.Multimap
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.registry.LanguageRegistry
import cpw.mods.fml.relauncher.Side
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util._
import net.minecraft.world.World

class ItemInventorySelector extends ItemMod(ModItemNames.InventorySelector){
  import ItemInventorySelector._
  setUnlocalizedName("item_selector")

  maxStackSize = 1
  setTextureName("compass")


  override def onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ)
    var tag = stack.getTagCompound
    if(tag == null){
      tag = new NBTTagCompound
      tag.setString(KEY_STATE, ItemInventorySelector.STATE_ABSOLUTE)
      stack.setTagCompound(tag)
    }

    var state = tag.getString(KEY_STATE)

    if(player.isSneaking){
      state = nextStateMapping.get(state).get
      println(state)
      tag.setString(KEY_STATE, state)
      val localizedName = LanguageRegistry.instance().getStringLocalization(getUnlocalizedName(stack))
      stack.setStackDisplayName(localizedName + "(" + state + ")")
    } else {
      state match{
        case STATE_ABSOLUTE => {
          val coordString = s"($x, $y, $z)}"
          player.addChatMessage(new ChatComponentText(s"Added $coordString to clipboard"))
          ClipBoard.set(coordString)
        }
        case STATE_RELATIVE => {
          val coords = stack.getTagCompound.getIntArray(KEY_COORDS)
          if(coords.length == 3){
            val diffString = s"~(${x - coords(0)}, ${y - coords(1)}, ${z - coords(2)})"
            if(FMLCommonHandler.instance().getSide == Side.CLIENT){
              player.addChatMessage(new ChatComponentText(s"Added $diffString to clipboard"))
              ClipBoard.set(diffString)
            }
            stack.getTagCompound.removeTag(KEY_COORDS)
          } else{
            val coords = Array(x, y, z)
            val coordString = s"${coords(0)}, ${coords(1)}, ${coords(2)}"
            player.addChatMessage(new ChatComponentText(s"First position: " + coordString))
            stack.getTagCompound.setIntArray(KEY_COORDS, coords)
          }
        }
        case STATE_NAME => {
          val name = s"<${LogisticFilterName.getName(world, x, y, z)}>"
          player.addChatMessage(new ChatComponentText(s"Added $name to clipboard"))
          ClipBoard.set(name)
        }
      }
    }
    true
  }
}

object ItemInventorySelector{
  val STATE_ABSOLUTE = "absolute"
  val STATE_RELATIVE = "relative"
  val STATE_NAME = "name"
  val KEY_COORDS = "coords"
  val KEY_STATE = "state"

  val nextStateMapping = Map(
    STATE_ABSOLUTE -> STATE_RELATIVE,
    STATE_RELATIVE -> STATE_NAME,
    STATE_NAME -> STATE_ABSOLUTE
  )

  val stateTextMapping = Map(
    STATE_ABSOLUTE -> "Click on a block to get its absolute coordinates",
    STATE_RELATIVE -> "Click on a block to set the anchor, then click on another to get relative coordinates to it",
    STATE_NAME -> "Click on a block to get its name"
  )
}
