package com.besuikerd.autologistics.common.lib.network

import com.besuikerd.autologistics.AutoLogistics
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.network.simpleimpl.{IMessage, MessageContext, IMessageHandler}
import net.minecraftforge.fml.relauncher.Side
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
