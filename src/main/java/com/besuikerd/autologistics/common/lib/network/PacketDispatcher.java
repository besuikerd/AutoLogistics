package com.besuikerd.autologistics.common.lib.network;

import com.besuikerd.autologistics.AutoLogistics;
import com.besuikerd.autologistics.common.ModConstants;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;

public class PacketDispatcher {
    public static final PacketDispatcher instance = new PacketDispatcher();

    private int discriminator;

    private PacketDispatcher(){
        discriminator = 0;
    }

    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(ModConstants.modId());

    public Packet getPacketFrom(IMessage message) {
        return CHANNEL.getPacketFrom(message);
    }

    public void sendToAll(IMessage message) {
        CHANNEL.sendToAll(message);
    }

    public void sendTo(IMessage message, EntityPlayerMP player) {
        CHANNEL.sendTo(message, player);
    }

    public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        CHANNEL.sendToAllAround(message, point);
    }

    public void sendToDimension(IMessage message, int dimensionId) {
        CHANNEL.sendToDimension(message, dimensionId);
    }

    public void sendToServer(IMessage message) {
        CHANNEL.sendToServer(message);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(IMessageHandler<REQ, REPLY> messageHandler, Class<REQ> requestMessageType, Side side) {
        CHANNEL.registerMessage(messageHandler, requestMessageType, discriminator++, side);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(ServerMessageHandler<REQ, REPLY> messageHandler, Class<REQ> requestMessageType) {
        registerMessage(messageHandler, requestMessageType, Side.SERVER);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(ClientMessageHandler<REQ, REPLY> messageHandler, Class<REQ> requestMessageType) {
        registerMessage(messageHandler, requestMessageType, Side.CLIENT);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(BiDirectionalMessageHandler<REQ, REPLY> messageHandler, Class<REQ> requestMessageType) {
        registerMessage(messageHandler, requestMessageType, Side.SERVER);
        registerMessage(messageHandler, requestMessageType, Side.CLIENT);
    }

    public void init(){
        registerMessage(new ClipboardMessageHandler(), ClipboardMessageHandler.ClipboardMessage.class);
    }
}
