package com.besuikerd.autologistics.lib.util

object int{
  implicit class IntExtensions(val i:Int) {
  }

  implicit class IntStringExtensions(val s:String){
    def optToInt:Option[Int] = try{
      Some(s.toInt)
    } catch{
      case e:NumberFormatException => None
    }
  }
}