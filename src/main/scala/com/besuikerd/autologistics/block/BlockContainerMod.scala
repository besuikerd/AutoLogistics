package com.besuikerd.autologistics.block

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

abstract class BlockContainerMod(material:Material) extends BlockContainer(material)
{
}