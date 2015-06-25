package com.besuikerd.autologistics.common.tile.traits

import com.besuikerd.autologistics.common.lib.inventory.IInventoryWrapper
import com.besuikerd.autologistics.common.tile._
import net.minecraft.block.BlockChest
import net.minecraft.inventory.{InventoryLargeChest, IInventory}
import net.minecraft.tileentity.{TileEntityChest, TileEntity}

import scala.collection.mutable.{Set => MSet, ArrayBuffer}
import scala.reflect._

trait TileCable extends TileEntityMod{
//  def findInventories: IndexedSeq[TileEntity with IInventory] = {
//    val foundInventories = findConnectedTiles[IInventory].map{
//      case t:TileEntityChest if t.getBlockType.isInstanceOf[BlockChest] => {
//        val blockChest = t.getBlockType.asInstanceOf[BlockChest]
//        blockChest.getLockableContainer(t.getWorld, t.getPos) match{
//          case inv:InventoryLargeChest => new TileChestWrapper(t, inv)
//          case other => t
//        }
//      }
//      case otherwise => otherwise
//    }
//
//    val foundDoubleChests = ArrayBuffer[TileEntityChest]()
//    val filteredInventories = foundInventories.filter{
//      case t:TileChestWrapper => if(!foundDoubleChests.exists(t.inventory.isPartOfLargeChest(_))){
//        foundDoubleChests += t.tile
//        true
//      } else false
//      case _ => true
//    }
//    filteredInventories
//  }
  //TODO fix double chests?

//  private class TileChestWrapper(override val tile:TileEntityChest, override val inventory:InventoryLargeChest) extends TileEntity with TileWrapper with IInventoryWrapper{
//    override def markDirty(): Unit = {
//      tile.markDirty()
//    }
//  }

//  def findConnectedTiles[A:ClassTag]: IndexedSeq[TileEntity with A] = {
//    val traversed = MSet[TileCable]()
//    val found = MSet[TileEntity with A]()
//    findConnectedTiles[A](traversed, found)
//    found.toIndexedSeq
//  }
//
//  private def findConnectedTiles[A:ClassTag](traversed:MSet[TileCable], inventories:MSet[TileEntity with A]): Unit = {
//    traversed += this
//    this.neighbours[A].foreach(n => inventories.add(n.asInstanceOf[TileEntity with A]))
//    for{
//      cable <- this.neighbours[TileCable] if !traversed.contains(cable)
//    } cable.findConnectedTiles[A](traversed, inventories)
//  }
}
