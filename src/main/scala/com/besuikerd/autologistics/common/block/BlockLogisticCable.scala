package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.common.{ModMaterials, ModBlockNames}
import com.besuikerd.autologistics.common.tile.{TileLogisticController, TileEntityLogisticCable}
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry

class BlockLogisticCable extends TileBlock(ModMaterials.machine, ModBlockNames.LogisticCable){
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileEntityLogisticCable

  override def register(): BlockLogisticCable.this.type = {
    super.register()
    GameRegistry.registerTileEntity(classOf[TileEntityLogisticCable], ModBlockNames.LogisticCable)
    this
  }
}