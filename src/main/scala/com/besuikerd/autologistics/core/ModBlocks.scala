package com.besuikerd.autologistics.core

import com.besuikerd.autologistics.block.BlockLogisticController
import com.besuikerd.autologistics.core.ModBlockNames
import com.besuikerd.autologistics.tile.TileLogisticController
import net.minecraft.block.material.Material
import net.minecraftforge.fml.common.registry.GameRegistry

object ModBlocks {
  final val LOGISTICS_CONTROLLER = new BlockLogisticController()

  def registerBlocks(): Unit ={
    GameRegistry.registerBlock(LOGISTICS_CONTROLLER, ModBlockNames.LOGISTICS_CONTROLLER)
  }

  def registerTiles(): Unit ={
    GameRegistry.registerTileEntity(classOf[TileLogisticController], ModBlockNames.LOGISTICS_CONTROLLER)
  }
}
