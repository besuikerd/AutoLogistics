package com.besuikerd.autologistics.common.lib.network

import com.besuikerd.autologistics.AutoLogistics
import cpw.mods.fml.common.network.simpleimpl.{MessageContext, IMessageHandler, IMessage}
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.player.EntityPlayer
import scala.annotation.switch

trait SidedMessageHandler[REQ <: IMessage, REPLY <: IMessage] extends IMessageHandler[REQ, REPLY] {
  override def onMessage(message: REQ, ctx: MessageContext): REPLY = {
    (ctx.side : @switch) match{
      case Side.CLIENT => onClientMessage(AutoLogistics.proxy.getPlayer(ctx), message, ctx)
      case Side.SERVER => onServerMessage(AutoLogistics.proxy.getPlayer(ctx), message, ctx)
    }
  }

  def onClientMessage(player:EntityPlayer, message:REQ, ctx:MessageContext): REPLY
  def onServerMessage(player:EntityPlayer, message:REQ, ctx:MessageContext): REPLY
}