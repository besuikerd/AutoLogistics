package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.common.{ModMaterials, ModBlockNames}
import com.besuikerd.autologistics.common.tile.{TileLogisticController, TileLogisticCable}
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.World

class BlockLogisticCable extends TileBlock(ModMaterials.machine, ModBlockNames.LogisticCable){
  setBlockTextureName("iron_block")

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileLogisticCable

  override def register(): BlockLogisticCable.this.type = {
    super.register()
    GameRegistry.registerTileEntity(classOf[TileLogisticCable], ModBlockNames.LogisticCable)
    this
  }
}