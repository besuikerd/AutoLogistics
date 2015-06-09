package com.besuikerd.autologistics.core

import com.besuikerd.autologistics.item.ItemInventorySelector

object ModItems {
  final val InventorySelector = new ItemInventorySelector().register()

  def init(): Unit ={

  }
}
