package com.besuikerd.autologistics.lib.nbt

import net.minecraft.nbt.{NBTTagInt, NBTTagString, NBTBase, NBTTagList}

object NBTHelper {
  def nbtList(elems: NBTBase*): NBTTagList = {
    val list = new NBTTagList()
    elems.foreach(list.appendTag)
    list
  }

  implicit def string2NBT(s:String): NBTTagString = new NBTTagString(s)
  implicit def int2NBT(i:Int): NBTTagInt = new NBTTagInt(i)
}
