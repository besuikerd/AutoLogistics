package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.AutoLogistics
import com.besuikerd.autologistics.common.ModConstants
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister


abstract class BlockMod(material:Material, val name:String) extends Block(material)
{
  setBlockName(ModConstants.modId + ":" + name)

  def register():this.type = {
    GameRegistry.registerBlock(this, name)
    this
  }
}