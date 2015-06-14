package com.besuikerd.autologistics.common.tile.traits

import com.besuikerd.autologistics.common.lib.inventory.IInventoryWrapper
import com.besuikerd.autologistics.common.tile._
import net.minecraft.block.BlockChest
import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.{TileEntityChest, TileEntity}

import scala.collection.mutable.{Set => MSet}
import scala.reflect._

trait TileCable extends TileEntityMod{
  def findInventories: IndexedSeq[TileEntity with IInventory] = findConnectedTiles[IInventory].map{
    case t:TileEntityChest if t.getBlockType.isInstanceOf[BlockChest] => {
      val blockChest = t.getBlockType.asInstanceOf[BlockChest]
      val inv = blockChest.getLockableContainer(t.getWorld, t.getPos)
      new TileEntity with TileWrapper with IInventoryWrapper{
        val tile = t
        val inventory = inv

        override def markDirty(): Unit = {
          tile.markDirty()
        }
      }
    }
    case otherwise => otherwise
  }

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
