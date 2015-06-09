package com.besuikerd.autologistics.tile.traits

import com.besuikerd.autologistics.tile._
import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockAccess

import scala.collection.mutable.{Set => MSet}
import scala.reflect._

trait TileCable extends TileEntityMod{
  def findInventories: IndexedSeq[TileEntity with IInventory] = findConnectedTiles[IInventory]


  def findConnectedTiles[A:ClassTag]: IndexedSeq[TileEntity with A] = {
    val traversed = MSet[TileCable]()
    val found = MSet[TileEntity with A]()
    findConnectedTiles[A](traversed, found)
    found.toIndexedSeq
  }

  private def findConnectedTiles[A:ClassTag](traversed:MSet[TileCable], inventories:MSet[TileEntity with A]): Unit = {
    traversed += this
    this.neighbours[A].foreach(n => inventories.add(n.asInstanceOf[TileEntity with A]))
    for{
      cable <- this.neighbours[TileCable] if !traversed.contains(cable)
    } cable.findConnectedTiles[A](traversed, inventories)
  }
}
