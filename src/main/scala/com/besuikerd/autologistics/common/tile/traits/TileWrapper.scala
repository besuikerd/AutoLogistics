package com.besuikerd.autologistics.common.tile.traits

import net.minecraft.block.Block
import net.minecraft.crash.CrashReportCategory
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World

trait TileWrapper extends TileEntity{
  def tile:TileEntity

  override def getWorldObj: World = tile.getWorldObj

  override def setWorldObj(p_145834_1_ : World): Unit = tile.setWorldObj(p_145834_1_)

  override def hasWorldObj: Boolean = tile.hasWorldObj

  override def receiveClientEvent(p_145842_1_ : Int, p_145842_2_ : Int): Boolean = tile.receiveClientEvent(p_145842_1_, p_145842_2_)

  override def getBlockType: Block = tile.getBlockType

  override def markDirty(): Unit = tile.markDirty()

  override def func_145828_a(p_145828_1_ : CrashReportCategory): Unit = tile.func_145828_a(p_145828_1_)

  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity): Unit = tile.onDataPacket(net, pkt)

  override def shouldRenderInPass(pass: Int): Boolean = tile.shouldRenderInPass(pass)

  override def writeToNBT(p_145841_1_ : NBTTagCompound): Unit = tile.writeToNBT(p_145841_1_)

  override def getDescriptionPacket: Packet = tile.getDescriptionPacket

  override def isInvalid: Boolean = tile.isInvalid

  override def getMaxRenderDistanceSquared: Double = tile.getMaxRenderDistanceSquared

  override def readFromNBT(p_145839_1_ : NBTTagCompound): Unit = tile.readFromNBT(p_145839_1_)

  override def updateEntity(): Unit = tile.updateEntity()

  override def validate(): Unit = tile.validate()

  override def getRenderBoundingBox: AxisAlignedBB = tile.getRenderBoundingBox

  override def invalidate(): Unit = tile.invalidate()

  override def canUpdate: Boolean = tile.canUpdate

  override def getBlockMetadata: Int = tile.getBlockMetadata

  override def shouldRefresh(oldBlock: Block, newBlock: Block, oldMeta: Int, newMeta: Int, world: World, x: Int, y: Int, z: Int): Boolean = tile.shouldRefresh(oldBlock, newBlock, oldMeta, newMeta, world, x, y, z)

  override def onChunkUnload(): Unit = tile.onChunkUnload()

  override def getDistanceFrom(p_145835_1_ : Double, p_145835_3_ : Double, p_145835_5_ : Double): Double = tile.getDistanceFrom(p_145835_1_, p_145835_3_, p_145835_5_)

  override def updateContainingBlockInfo(): Unit = tile.updateContainingBlockInfo()
}