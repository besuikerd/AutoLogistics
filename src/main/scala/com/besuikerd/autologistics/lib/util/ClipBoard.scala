package com.besuikerd.autologistics.lib.util

import java.awt.Toolkit
import java.awt.datatransfer._
import java.io.IOException

object ClipBoard {
  val systemClipboard = Toolkit.getDefaultToolkit.getSystemClipboard
  val clipboardOwner:ClipboardOwner = new ClipboardOwner {
    override def lostOwnership(clipboard: Clipboard, contents: Transferable): Unit = {}
  }

  def set(value:String): Unit ={
    systemClipboard.setContents(new StringSelection(value), clipboardOwner)
  }

  def get(): Option[String] ={
    val transferable = systemClipboard.getContents(null)
    if(transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)){
      try{
        Some(transferable.getTransferData(DataFlavor.stringFlavor).toString)
      } catch{case e:IOException => None}
    } else None
  }
}
