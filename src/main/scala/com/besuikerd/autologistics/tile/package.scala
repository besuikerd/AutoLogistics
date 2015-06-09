package com.besuikerd.autologistics

import net.minecraft.tileentity.TileEntity
import scala.collection.mutable.ArrayBuffer
import scala.reflect._
import scala.reflect.ClassTag
import com.besuikerd.autologistics.block._

package object tile {
  implicit class TileEntityExtensions(val tile:TileEntity) extends AnyVal{

    def neighbours[A : ClassTag]: Iterable[A] = {
      val tag = classTag[A].runtimeClass
      val result = ArrayBuffer[A]()
      if(tile != null && tile.getWorld != null && tile.getPos != null) {
        for (pos <- tile.getPos.sides) {
          val neighbour = tile.getWorld.getTileEntity(pos)
          if (tag.isInstance(neighbour)) {
            result += neighbour.asInstanceOf[A]
          }
        }
      }
      result
    }
  }
}
