package com.besuikerd.autologistics.common.lib.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import javax.swing.text.Position;

public abstract class PositionalMessage implements IMessage{
    protected int x;
    protected int y;
    protected int z;

    public PositionalMessage(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PositionalMessage(){

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
