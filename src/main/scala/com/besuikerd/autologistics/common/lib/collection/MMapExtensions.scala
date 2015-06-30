package com.besuikerd.autologistics.common.lib.collection

import scala.reflect._
import scala.collection.mutable.Map

object MMapExtensions {
  implicit class MMapExtensions[K, V](val map:Map[K,V]) extends AnyVal{
    def getOfType[T <: V :ClassTag](key:K):Option[T] = map.get(key) match{
      case Some(x) if classTag[T].runtimeClass.isInstance(x) => Some(x.asInstanceOf[T])
      case _ => None
    }
  }
}
