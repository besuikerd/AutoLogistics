package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.AutoLogistics
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraftforge.fml.common.registry.GameRegistry


abstract class BlockMod(material:Material, val name:String) extends Block(material)
{
  setUnlocalizedName(AutoLogistics.MOD_ID + ":" + name)

  def register():this.type = {
    GameRegistry.registerBlock(this, name)
    this
  }
}