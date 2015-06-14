package com.besuikerd.autologistics.client

import com.besuikerd.autologistics.common.CommonProxy
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side

import scala.annotation.switch

class ClientProxy extends CommonProxy{
  override def getPlayer(ctx: MessageContext): EntityPlayer = (ctx.side : @switch) match{
    case Side.CLIENT => Minecraft.getMinecraft.thePlayer
    case Side.SERVER => super.getPlayer(ctx)
  }
}