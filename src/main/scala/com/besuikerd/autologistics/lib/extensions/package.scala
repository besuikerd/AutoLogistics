package com.besuikerd.autologistics.lib

import scala.collection.mutable.Stack

package object extensions {
  implicit class StackExtensions[A](val stack:Stack[A]) extends AnyVal{

  }

  implicit class IntStringExtensions(val s:String){
    def optToInt:Option[Int] = try{
      Some(s.toInt)
    } catch{
      case e:NumberFormatException => None
    }
  }
}
