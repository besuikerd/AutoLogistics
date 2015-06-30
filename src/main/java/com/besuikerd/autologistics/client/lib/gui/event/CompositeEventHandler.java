package com.besuikerd.autologistics.client.lib.gui.event;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.common.lib.registry.ListRegistry;

public class CompositeEventHandler extends ListRegistry<IEventHandler> implements IEventHandler{
    @Override
    public void post(String name, Element e, Object... args) {
        for(IEventHandler handler: registry){
            handler.post(name, e, args);
        }
    }
}
