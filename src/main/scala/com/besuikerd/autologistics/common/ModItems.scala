package com.besuikerd.autologistics.common

import com.besuikerd.autologistics.common.item.ItemInventorySelector

object ModItems {
  final val InventorySelector = new ItemInventorySelector().register()

  def init(): Unit ={

  }
}
