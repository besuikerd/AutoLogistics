package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.AutoLogistics
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material


abstract class BlockMod(material:Material, val name:String) extends Block(material)
{
  setBlockName(AutoLogistics.MOD_ID + ":" + name)

  def register():this.type = {
    GameRegistry.registerBlock(this, name)
    this
  }
}