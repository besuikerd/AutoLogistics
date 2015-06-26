package com.besuikerd.autologistics.common.lib.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public abstract class ServerMessageHandler<REQ extends IMessage, REPLY extends IMessage> extends SidedMessageHandler<REQ, REPLY> {
    @Override
    public REPLY onClientMessage(EntityPlayer player, REQ message, MessageContext ctx) {
        return null;
    }
}
