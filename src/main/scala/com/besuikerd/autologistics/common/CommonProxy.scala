package com.besuikerd.autologistics.common

import cpw.mods.fml.common.network.simpleimpl.MessageContext
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.player.EntityPlayer

class CommonProxy {
  def getPlayer(ctx:MessageContext):EntityPlayer = ctx.getServerHandler.playerEntity

  def init(): Unit = {}

  def getSide = Side.SERVER
  def getSideOfThread = if(getSide == Side.CLIENT && Thread.currentThread().getName.equals("Server thread")) Side.SERVER else getSide
}
