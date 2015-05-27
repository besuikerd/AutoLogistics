package com.besuikerd.autologistics.block

import com.besuikerd.autologistics.core.ModMaterials
import com.besuikerd.autologistics.tile.TileLogisticController
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockLogisticController extends BlockContainerMod(ModMaterials.machine){
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileLogisticController()
}
