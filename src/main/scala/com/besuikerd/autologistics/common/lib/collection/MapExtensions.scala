package com.besuikerd.autologistics.common.lib.collection

import scala.collection.MapLike
import scala.reflect.ClassTag
import scala.reflect._

object MapExtensions {
  implicit class MapExtensions[K, V](val map:Map[K,V]) extends AnyVal{
    def getOfType[T <: V :ClassTag](key:K):Option[T] = map.get(key) match{
      case Some(x) if classTag[T].runtimeClass.isInstance(x) => Some(x.asInstanceOf[T])
      case _ => None
    }
  }
}
