package com.besuikerd.autologistics.common.lib.nbt

import net.minecraft.nbt._
import scala.collection.mutable.{Map => MMap}

object NBTHelper {
  def nbtList(elems: NBTBase*): NBTTagList = {
    val list = new NBTTagList()
    elems.foreach(list.appendTag)
    list
  }

  implicit def string2NBT(s:String): NBTTagString = new NBTTagString(s)
  implicit def int2NBT(i:Int): NBTTagInt = new NBTTagInt(i)
  implicit def double2NBT(d:Double): NBTTagDouble = new NBTTagDouble(d)

  def pairs2NBT[A <: NBTBase](pairs:Iterable[(String, A)]):NBTTagCompound = {
    val nbt = new NBTTagCompound
    for ((k, v) <- pairs) {
      nbt.setTag(k, v)
    }
    nbt
  }

  def nbtMap(kv :(String, NBTBase)*):NBTTagCompound = {
    pairs2NBT(kv.toIterable)
  }
}
