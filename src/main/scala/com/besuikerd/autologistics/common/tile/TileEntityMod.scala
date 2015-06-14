package com.besuikerd.autologistics.common.tile

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity

abstract class TileEntityMod extends TileEntity{
  override def getDescriptionPacket: Packet = {
    val compound = new NBTTagCompound();
    writeToNBT(compound)
    new S35PacketUpdateTileEntity(pos, 1, compound);
  }

  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity): Unit = {
    readFromNBT(pkt.getNbtCompound)
  }
}