package com.besuikerd.autologistics.common.lib.collection

import scala.collection.TraversableLike
import scala.collection.generic.CanBuildFrom


object TraversableExtensions {
  implicit class TraversableExtensions[+A, Repr<:TraversableLike[A,Repr]](val traversable:TraversableLike[A,Repr]) extends AnyVal{
    def foldWhile[B](initial:B)(pred:B => Boolean)(f:(B, A) => B)(implicit cbf:CanBuildFrom[Repr, A, Repr]): (B, Repr) = {
      var acc = initial
      var self = traversable
      while(pred(acc) && self.nonEmpty){
        acc = f(acc, self.head)
        if(self.nonEmpty) {
          self = self.tail
        }
      }
      (acc, self.asInstanceOf[Repr])
    }

    def countWhile[B](pred:B => Boolean)(f:A => B)(implicit cbf:CanBuildFrom[Repr, A, Repr], numeric:Numeric[B]): (B, Repr) = {
      foldWhile(numeric.zero)(pred)((acc, cur) => numeric.plus(acc, f(cur)))(cbf)
    }
  }
}