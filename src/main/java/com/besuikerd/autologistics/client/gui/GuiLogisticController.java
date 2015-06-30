package com.besuikerd.autologistics.client.gui;

import com.besuikerd.autologistics.client.lib.gui.GuiBase;
import com.besuikerd.autologistics.client.lib.gui.element.*;
import com.besuikerd.autologistics.client.lib.gui.element.adapter.ButtonElementAdapter;
import com.besuikerd.autologistics.client.lib.gui.event.Trigger;
import com.besuikerd.autologistics.client.lib.gui.layout.HorizontalLayout;
import com.besuikerd.autologistics.client.lib.gui.layout.VerticalLayout;

public class GuiLogisticController extends GuiBase{
    @Override
    public void init() {
        root.add(
            new ElementScrollContainer(200).add(
                new ElementButton(100, 200, "hoi")
            )
        );
    }

    public void addButton(ElementButton e){
        e.getParent().add(new ElementButton("Add").trigger(Trigger.RELEASED, "addButton"));
    }
}
