package com.besuikerd.autologistics.block

import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material

abstract class TileBlock(material:Material, name:String) extends BlockMod(material, name) with ITileEntityProvider{
}
