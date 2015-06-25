package com.besuikerd.autologistics.common.lib.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;


public abstract class AbstractPacketDispatcher {
    public final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel("autologistics");
    private int discriminator = 0;

    public void sendTo(IMessage msg, EntityPlayerMP player) {
        channel.sendTo(msg, player);
    }

    public void sendToAll(IMessage msg){
        channel.sendToAll(msg);
    }

    public void sendToAllAround(IMessage msg, NetworkRegistry.TargetPoint point){
        channel.sendToAllAround(msg, point);
    }

    public void sendToDimension(IMessage msg, int dimensionId){
        channel.sendToDimension(msg, dimensionId);
    }

    public Packet getPacketFrom(IMessage msg) {
        return channel.getPacketFrom(msg);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(IMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestMessageType, Side side) {
        channel.registerMessage(messageHandler, requestMessageType, discriminator++, side);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(ClientMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestMessageType) {
        registerMessage(messageHandler, requestMessageType, Side.CLIENT);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(ServerMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestMessageType) {
        registerMessage(messageHandler, requestMessageType, Side.SERVER);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(BiDirectionalMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestMessageType) {
        registerMessage(messageHandler, requestMessageType, Side.SERVER);
        registerMessage(messageHandler, requestMessageType, Side.CLIENT);
    }
}
