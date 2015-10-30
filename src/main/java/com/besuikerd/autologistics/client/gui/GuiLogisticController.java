package com.besuikerd.autologistics.client.gui;

import com.besuikerd.autologistics.client.lib.gui.GuiTileEntity;
import com.besuikerd.autologistics.client.lib.gui.element.*;
import com.besuikerd.autologistics.client.lib.gui.event.Trigger;
import com.besuikerd.autologistics.client.lib.gui.layout.HorizontalLayout;
import com.besuikerd.autologistics.client.lib.gui.layout.VerticalLayout;


public class GuiLogisticController extends GuiVirtualMachine{

    @Override
    public void init() {
        root.add(
            new ElementLabel("Logistics Controller").width(250),
            new ElementContainer().layout(new HorizontalLayout()).add(
                    new ElementScrollableTextArea(250, 200).text(tile.program().getValue()).id("program"),
                    new ElementContainer().layout(new VerticalLayout(0, 2)).add(
                            new ElementButton(60, "Compile").trigger(Trigger.PRESSED, "compile"),
                            new ElementButton(60, "Run").trigger(Trigger.PRESSED, "run"),
                            new ElementButton(60, "Kill").trigger(Trigger.PRESSED, "kill")
                    ).padding(2)
            ),
            new ElementLabel("").textColor(0xffe50000).id("error").paddingVertical(10)
        ).trigger(Trigger.GUI_CLOSED, "saveProgram");
        root.update();

        root.lookup("program").requestFocus();
    }

    public void addButton(ElementButton e){
        e.getParent().add(new ElementButton("Add").trigger(Trigger.RELEASED, "addButton"));
    }
}
