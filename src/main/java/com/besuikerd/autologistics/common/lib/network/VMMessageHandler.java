package com.besuikerd.autologistics.common.lib.network;

import com.besuikerd.autologistics.common.tile.traits.TileVirtualMachine;

public abstract class VMMessageHandler<A extends PositionalMessage> extends TileEntityMessageHandler<A, TileVirtualMachine>{
    public VMMessageHandler() {
        super(TileVirtualMachine.class);
    }
}
