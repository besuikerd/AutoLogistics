package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.common.{ModMaterials, ModBlockNames}
import com.besuikerd.autologistics.common.tile.TileLogisticController
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.World

class BlockLogisticController extends TileBlock(ModMaterials.machine, ModBlockNames.LogisticController){
  setBlockTextureName("endframe_top")

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileLogisticController()

  override def register(): BlockLogisticController.this.type = {
    super.register()
    GameRegistry.registerTileEntity(classOf[TileLogisticController], ModBlockNames.LogisticController)
    this
  }
}
