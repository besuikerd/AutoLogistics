package com.besuikerd.autologistics.client.gui;

import com.besuikerd.autologistics.client.lib.gui.GuiBase;
import com.besuikerd.autologistics.client.lib.gui.element.*;
import com.besuikerd.autologistics.client.lib.gui.event.Trigger;
import net.minecraft.client.Minecraft;

public class GuiLogisticController extends GuiBase{
    @Override
    public void init() {
        root.add(
                new ElementTextArea("hello\nworld", 200)
        );
    }

    public void addButton(ElementButton e){
        e.getParent().add(new ElementButton("Add").trigger(Trigger.RELEASED, "addButton"));
    }
}
