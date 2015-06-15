package com.besuikerd.autologistics.common.tile.traits

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.crash.CrashReportCategory
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{AxisAlignedBB, BlockPos}
import net.minecraft.world.World

trait TileWrapper extends TileEntity{
  def tile:TileEntity

  override def setWorldObj(worldIn: World): Unit = tile.setWorldObj(worldIn)

  override def getTileData: NBTTagCompound = tile.getTileData

  override def hasWorldObj: Boolean = tile.hasWorldObj

  override def receiveClientEvent(id: Int, `type`: Int): Boolean = tile.receiveClientEvent(id, `type`)

  override def getPos: BlockPos = tile.getPos

  override def getBlockType: Block = tile.getBlockType

  override def getDistanceSq(x: Double, y: Double, z: Double): Double = tile.getDistanceSq(x, y, z)

  override def markDirty(): Unit = tile.markDirty()

  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity): Unit = tile.onDataPacket(net, pkt)

  override def shouldRenderInPass(pass: Int): Boolean = tile.shouldRenderInPass(pass)

  override def writeToNBT(compound: NBTTagCompound): Unit = tile.writeToNBT(compound)

  override def getDescriptionPacket: Packet = tile.getDescriptionPacket

  override def setPos(posIn: BlockPos): Unit = tile.setPos(posIn)

  override def addInfoToCrashReport(reportCategory: CrashReportCategory): Unit = tile.addInfoToCrashReport(reportCategory)

  override def isInvalid: Boolean = tile.isInvalid

  override def getMaxRenderDistanceSquared: Double = tile.getMaxRenderDistanceSquared

  override def readFromNBT(compound: NBTTagCompound): Unit = tile.readFromNBT(compound)

  override def getWorld: World = tile.getWorld

  override def validate(): Unit = tile.validate()

  override def getRenderBoundingBox: AxisAlignedBB = tile.getRenderBoundingBox

  override def invalidate(): Unit = tile.invalidate()

  override def canRenderBreaking: Boolean = tile.canRenderBreaking

  override def getBlockMetadata: Int = tile.getBlockMetadata

  override def shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newSate: IBlockState): Boolean = tile.shouldRefresh(world, pos, oldState, newSate)

  override def onChunkUnload(): Unit = tile.onChunkUnload()

  override def updateContainingBlockInfo(): Unit = tile.updateContainingBlockInfo()
}