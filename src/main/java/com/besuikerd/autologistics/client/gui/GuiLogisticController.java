package com.besuikerd.autologistics.client.gui;

import com.besuikerd.autologistics.client.lib.gui.GuiBase;
import com.besuikerd.autologistics.client.lib.gui.element.ElementButton;
import com.besuikerd.autologistics.client.lib.gui.event.Trigger;

public class GuiLogisticController extends GuiBase{
    @Override
    public void init() {
        root.add(
            new ElementButton("Hello World!").trigger(Trigger.RELEASED, "hello")
        );
    }
}
