package com.besuikerd.autologistics.common.lib.network;

import com.besuikerd.autologistics.common.tile.traits.TileVirtualMachine;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class KillProgramMessageHandler extends VMMessageHandler<KillProgramMessageHandler.KillProgramMessage> {
    @Override
    public void onTileMessage(TileVirtualMachine tile, EntityPlayer player, KillProgramMessage message, MessageContext ctx) {
        tile.virtualMachine().reset();
    }

    public static class KillProgramMessage extends PositionalMessage{
        public KillProgramMessage(int x, int y, int z) {
            super(x, y, z);
        }

        public KillProgramMessage() {

        }
    }
}
