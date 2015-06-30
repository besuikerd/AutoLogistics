package com.besuikerd.autologistics.client.gui;

import com.besuikerd.autologistics.common.lib.util.INamed;
import net.minecraft.inventory.Container;

public interface IGuiEntry extends INamed {
	public Class<? extends Container> getContainerClass();
	public Class<? extends GuiBase> getGuiClass();
	public IGuiBinder getBinder();
}
