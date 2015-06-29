package com.besuikerd.autologistics

import com.besuikerd.autologistics.common.command.{OreDictCommand, ItemIdCommand}
import com.besuikerd.autologistics.common.lib.network.PacketDispatcher
import com.besuikerd.autologistics.common.{CommonProxy, ModItems, ModBlocks}
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLServerStartingEvent, FMLInitializationEvent}
import cpw.mods.fml.common.{SidedProxy, Mod}


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
    PacketDispatcher.instance.init()
  }

  @EventHandler
  def serverLoad(event: FMLServerStartingEvent): Unit ={
    event.registerServerCommand(new ItemIdCommand)
    event.registerServerCommand(new OreDictCommand)
  }
}