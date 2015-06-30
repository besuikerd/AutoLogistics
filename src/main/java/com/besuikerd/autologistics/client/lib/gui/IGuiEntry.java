package com.besuikerd.autologistics.client.lib.gui;

import com.besuikerd.autologistics.common.lib.util.INamed;
import net.minecraft.inventory.Container;

public interface IGuiEntry extends INamed {
	Class<? extends Container> getContainerClass();
	Class<? extends GuiBase> getGuiClass();
	IGuiBinder getBinder();
}
