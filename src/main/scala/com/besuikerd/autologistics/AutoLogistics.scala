package com.besuikerd.autologistics

import com.besuikerd.autologistics.common.{CommonProxy, ModItems, ModBlocks}
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.{SidedProxy, Mod}
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.relauncher.Side


@Mod(name = AutoLogistics.MOD_NAME, modid = AutoLogistics.MOD_ID, version = AutoLogistics.MOD_VERSION, modLanguage = "scala")
object AutoLogistics {
  final val MOD_NAME = "AutoLogistics"
  final val MOD_ID = "autologistics"
  final val MOD_VERSION = "0.01"


  @SidedProxy(serverSide = "com.besuikerd.autologistics.common.CommonProxy", clientSide = "com.besuikerd.autologistics.client.ClientProxy")
  var proxy:CommonProxy = null

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {
    ModBlocks.init()
    ModItems.init()
  }
}