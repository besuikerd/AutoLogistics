package com.besuikerd.autologistics

import com.besuikerd.autologistics.client.gui.GuiEntries
import com.besuikerd.autologistics.client.lib.gui.IGuiEntry
import com.besuikerd.autologistics.common.command.{OreDictCommand, ItemIdCommand}
import com.besuikerd.autologistics.common.lib.network.PacketDispatcher
import com.besuikerd.autologistics.common.{ModBlocks, ModItems, CommonProxy, ModConstants}
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLServerStartingEvent, FMLInitializationEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.{SidedProxy, Mod}


@Mod(name = ModConstants.modName, modid = ModConstants.modVersion, version = ModConstants.modVersion, modLanguage = "scala")
object AutoLogistics {

  @SidedProxy(serverSide = "com.besuikerd.autologistics.common.CommonProxy", clientSide = "com.besuikerd.autologistics.client.ClientProxy")
  var proxy:CommonProxy = null

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {
    ModBlocks.init()
    ModItems.init()

    //register packet handler
    PacketDispatcher.instance.init()

    //register gui handlers
    NetworkRegistry.INSTANCE.registerGuiHandler(AutoLogistics, GuiRegistry.instance)
    for(entry <- GuiEntries.values()){
      GuiRegistry.instance.register(entry);
    }
  }

  @EventHandler
  def serverLoad(event: FMLServerStartingEvent): Unit ={
    event.registerServerCommand(new ItemIdCommand)
    event.registerServerCommand(new OreDictCommand)
  }
}