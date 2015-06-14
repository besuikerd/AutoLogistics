package com.besuikerd.autologistics.common

import com.besuikerd.autologistics.common.block.{BlockLogisticCable, BlockLogisticController}

object ModBlocks {
  final val LogisticController = new BlockLogisticController().register()
  final val LogisticCable = new BlockLogisticCable().register()

  def init(): Unit = {

  }
}
