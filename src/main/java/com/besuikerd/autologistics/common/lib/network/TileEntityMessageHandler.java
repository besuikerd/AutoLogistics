package com.besuikerd.autologistics.common.lib.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityMessageHandler<A extends PositionalMessage, T> extends ServerMessageHandler<A, IMessage>{
    private Class<T> cls;

    public TileEntityMessageHandler(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public IMessage onServerMessage(EntityPlayer player, A message, MessageContext ctx) {
        TileEntity tile = player.worldObj.getTileEntity(message.getX(), message.getY(), message.getZ());
        if(tile != null && cls.isInstance(tile)){
            onTileMessage(cls.cast(tile), player, message, ctx);
        }
        return null;
    }

    public abstract void onTileMessage(T tile, EntityPlayer player, A message, MessageContext ctx);
}
