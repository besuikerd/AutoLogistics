package com.besuikerd.autologistics.common.tile.traits

import com.besuikerd.autologistics.client.lib.gui.element.Element
import com.besuikerd.autologistics.client.lib.gui.event.{IEventHandler, EventHandler}

trait TileEventHandler extends IEventHandler{
  val eventHandler = new EventHandler(handlerObject)

  override def post(name: String, e: Element, args: AnyRef*): Unit = eventHandler.post(name, e, args)
  def handlerObject:Any
}
