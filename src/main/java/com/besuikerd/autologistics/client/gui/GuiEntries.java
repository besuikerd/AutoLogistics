package com.besuikerd.autologistics.client.gui;

import com.besuikerd.autologistics.client.lib.gui.GuiBase;
import com.besuikerd.autologistics.client.lib.gui.GuiBinder;
import com.besuikerd.autologistics.client.lib.gui.IGuiBinder;
import com.besuikerd.autologistics.client.lib.gui.IGuiEntry;
import com.besuikerd.autologistics.common.inventory.ContainerBesu;
import net.minecraft.inventory.Container;

public enum GuiEntries implements IGuiEntry{
    LogisticController("logistic_controller", ContainerBesu.class, GuiLogisticController.class, GuiBinder.TILE_ENTITY)
    ;

    private final String name;
    private final Class<? extends Container> containerClass;
    private final Class<? extends GuiBase> guiClass;
    private final IGuiBinder binder;

    GuiEntries(String name, Class<? extends Container> containerClass, Class<? extends GuiBase> guiClass, IGuiBinder binder) {
        this.name = name;
        this.containerClass = containerClass;
        this.guiClass = guiClass;
        this.binder = binder;
    }

    public Class<? extends Container> getContainerClass() {
        return containerClass;
    }

    public Class<? extends GuiBase> getGuiClass() {
        return guiClass;
    }

    public IGuiBinder getBinder() {
        return binder;
    }

    @Override
    public String getName() {
        return name;
    }
}
