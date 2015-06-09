package com.besuikerd.autologistics.core.dsl

import com.besuikerd.autologistics.item.ItemInventorySelector

object ModItems {
  final val InventorySelector = new ItemInventorySelector().register()

  def init(): Unit ={

  }
}
