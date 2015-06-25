package com.besuikerd.autologistics.common

import com.besuikerd.autologistics.common.lib.util.WorldUtil
import net.minecraft.entity.Entity
import net.minecraft.tileentity.TileEntity
import java.util.{List => JList}
import scala.reflect._

package object tile {
  implicit class TileEntityExtensions[T <: TileEntity](val tile: T) extends AnyVal{
    def getEntitiesWithinRange[A <: Entity : ClassTag](range:Int): JList[A] ={
      val cls = classTag[A].runtimeClass.asInstanceOf[Class[A]]
      WorldUtil.getEntitiesWithinRange(cls, tile.getWorldObj, tile.xCoord, tile.yCoord, tile.zCoord, range)
    }
  }
}
