package com.besuikerd.autologistics.common.lib.network

import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.network.simpleimpl.{IMessage, MessageContext}

trait ClientMessageHandler[REQ <: IMessage, REPLY <: IMessage] extends SidedMessageHandler[REQ, REPLY]{
  override def onServerMessage(player: EntityPlayer, message: REQ, ctx: MessageContext): REPLY = null.asInstanceOf[REPLY]
}
