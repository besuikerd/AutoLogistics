package com.besuikerd.autologistics.client.gui;

import com.besuikerd.autologistics.client.lib.gui.GuiTileEntity;
import com.besuikerd.autologistics.common.tile.traits.TileVirtualMachine;

public abstract class GuiVirtualMachine extends GuiTileEntity<TileVirtualMachine>{
    public GuiVirtualMachine() {
        super(TileVirtualMachine.class);
    }
}
