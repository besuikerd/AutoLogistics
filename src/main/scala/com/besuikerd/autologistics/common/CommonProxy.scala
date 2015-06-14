package com.besuikerd.autologistics.common


import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side

import scala.annotation.switch

class CommonProxy {
  def getPlayer(ctx:MessageContext):EntityPlayer = ctx.getServerHandler.playerEntity
}
