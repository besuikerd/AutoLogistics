package com.besuikerd.autologistics.common.lib.network;

import com.besuikerd.autologistics.common.tile.traits.TileVirtualMachine;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class LoadProgramMessageHandler extends VMMessageHandler<LoadProgramMessageHandler.LoadProgramMessage>{

    @Override
    public void onTileMessage(TileVirtualMachine tile, EntityPlayer player, LoadProgramMessage message, MessageContext ctx) {
        tile.program().setValue(message.getProgram());
    }

    public static class LoadProgramMessage extends PositionalMessage{
        private String program;

        public LoadProgramMessage(int x, int y, int z, String program) {
            super(x, y, z);
            this.program = program;
        }

        public LoadProgramMessage() {
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            super.fromBytes(buf);
            this.program = ByteBufUtils.readUTF8String(buf);
        }

        @Override
        public void toBytes(ByteBuf buf) {
            super.toBytes(buf);
            ByteBufUtils.writeUTF8String(buf, program);
        }

        public String getProgram() {
            return program;
        }
    }

    @Override
    public boolean handlesClientSide() {
        return true;
    }
}
