package com.besuikerd.autologistics

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent


@Mod(name = AutoLogistics.MOD_NAME, modid = AutoLogistics.MOD_NAME, version = "0.0.1", modLanguage = "scala")
object AutoLogistics {
  final val MOD_NAME = "AutoLogistics"

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {

  }
}