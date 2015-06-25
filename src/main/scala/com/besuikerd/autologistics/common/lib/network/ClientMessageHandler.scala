package com.besuikerd.autologistics.common.lib.network

import cpw.mods.fml.common.network.simpleimpl.{MessageContext, IMessage}
import net.minecraft.entity.player.EntityPlayer


trait ClientMessageHandler[REQ <: IMessage, REPLY <: IMessage] extends SidedMessageHandler[REQ, REPLY]{
  override def onServerMessage(player: EntityPlayer, message: REQ, ctx: MessageContext): REPLY = null.asInstanceOf[REPLY]
}
