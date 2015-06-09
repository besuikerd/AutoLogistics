package com.besuikerd.autologistics.core

import com.besuikerd.autologistics.block.{BlockLogisticCable, BlockLogisticController}
import com.besuikerd.autologistics.tile.TileLogisticController
import net.minecraftforge.fml.common.registry.GameRegistry

object ModBlocks {
  final val LogisticController = new BlockLogisticController().register()
  final val LogisticCable = new BlockLogisticCable().register()

  def init(): Unit = {

  }
}
