package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.common.{ModMaterials, ModBlockNames}
import com.besuikerd.autologistics.common.tile.{TileLogisticController, TileEntityLogisticCable}
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockLogisticCable extends TileBlock(ModMaterials.machine, ModBlockNames.LogisticCable){
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileEntityLogisticCable

  override def register(): BlockLogisticCable.this.type = {
    super.register()
    GameRegistry.registerTileEntity(classOf[TileEntityLogisticCable], ModBlockNames.LogisticCable)
    this
  }
}