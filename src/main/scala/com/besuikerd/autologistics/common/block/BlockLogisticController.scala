package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.common.{ModMaterials, ModBlockNames}
import com.besuikerd.autologistics.common.tile.TileLogisticController
import net.minecraft.block.material.Material
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockLogisticController extends TileBlock(ModMaterials.machine, ModBlockNames.LogisticController){

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileLogisticController()

  override def register(): BlockLogisticController.this.type = {
    super.register()
    GameRegistry.registerTileEntity(classOf[TileLogisticController], ModBlockNames.LogisticController)
    this
  }
}
