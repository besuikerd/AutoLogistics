package com.besuikerd.autologistics.item

import net.minecraft.item.Item
import net.minecraftforge.fml.common.registry.GameRegistry

class ItemMod(name:String) extends Item{
  def register(): this.type ={
    GameRegistry.registerItem(this, name)
    this
  }
}
