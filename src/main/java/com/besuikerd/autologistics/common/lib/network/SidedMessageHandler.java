package com.besuikerd.autologistics.common.lib.network;

import com.besuikerd.autologistics.AutoLogistics;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public abstract class SidedMessageHandler<REQ extends IMessage, REPLY extends IMessage> implements IMessageHandler<REQ, REPLY>{
    @Override
    public REPLY onMessage(REQ message, MessageContext ctx) {
        switch(ctx.side){
            case CLIENT:
                return onClientMessage(AutoLogistics.proxy().getPlayer(ctx), message, ctx);
            case SERVER:
                return onServerMessage(AutoLogistics.proxy().getPlayer(ctx), message, ctx);
            default:
                return null;
        }
    }

    public abstract REPLY onClientMessage(EntityPlayer player, REQ message, MessageContext ctx);
    public abstract REPLY onServerMessage(EntityPlayer player, REQ message, MessageContext ctx);
}
