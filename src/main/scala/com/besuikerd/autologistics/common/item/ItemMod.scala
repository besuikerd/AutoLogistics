package com.besuikerd.autologistics.common.item

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item


class ItemMod(name:String) extends Item{
  def register(): this.type ={
    GameRegistry.registerItem(this, name)
    this
  }
}
