package com.besuikerd.autologistics.common.lib.network;

import com.besuikerd.autologistics.common.tile.traits.TileVirtualMachine;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class RunProgramMessageHandler extends VMMessageHandler<RunProgramMessageHandler.RunProgramMessage>{

    @Override
    public void onTileMessage(TileVirtualMachine tile, EntityPlayer player, RunProgramMessage message, MessageContext ctx) {
        tile.load(tile.program().getValue());
    }

    public static class RunProgramMessage extends PositionalMessage{

        public RunProgramMessage() {
        }

        public RunProgramMessage(int x, int y, int z) {
            super(x, y, z);
        }
    }
}
