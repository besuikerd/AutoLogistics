package com.besuikerd.autologistics.common.block

import com.besuikerd.autologistics.client.gui.GuiEntries
import com.besuikerd.autologistics.client.lib.gui.IGuiEntry
import com.besuikerd.autologistics.common.{ModMaterials, ModBlockNames}
import com.besuikerd.autologistics.common.tile.TileLogisticController
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockLogisticController extends TileBlock(ModMaterials.machine, ModBlockNames.LogisticController)
with BlockGui
{
  setBlockTextureName("endframe_top")


  override def guiEntry: IGuiEntry = GuiEntries.LogisticController

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileLogisticController()

  override def register(): BlockLogisticController.this.type = {
    super.register()
    GameRegistry.registerTileEntity(classOf[TileLogisticController], ModBlockNames.LogisticController)
    this
  }
}
