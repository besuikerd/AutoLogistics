package com.besuikerd.autologistics.client.gui.event;

import com.besuikerd.autologistics.client.gui.element.Element;
import com.besuikerd.autologistics.client.gui.element.ElementRootContainer;

public interface IEventAction {
	public void onEvent(String name, Object[] args, ElementRootContainer root, Element e);
}
