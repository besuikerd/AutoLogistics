package com.besuikerd.autologistics.client.lib.gui.event;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.client.lib.gui.element.ElementRootContainer;

public interface IEventAction {
	public void onEvent(String name, Object[] args, ElementRootContainer root, Element e);
}
