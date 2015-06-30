package com.besuikerd.autologistics.client.gui.event;

import com.besuikerd.autologistics.client.gui.element.Element;
import com.besuikerd.autologistics.client.gui.element.ElementRootContainer;

public interface ITrigger {
	public void trigger(String name, ElementRootContainer root, Element e, Object... args);
}
