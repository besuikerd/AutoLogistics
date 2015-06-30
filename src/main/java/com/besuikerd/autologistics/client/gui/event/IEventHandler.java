package com.besuikerd.autologistics.client.gui.event;

import com.besuikerd.autologistics.client.gui.element.Element;

public interface IEventHandler {
	/**
	 * posts an event to this IEventHandler. implementations should process
	 * these events in some way
	 * 
	 * @param name
	 * @param e
	 * @param args
	 */
	public void post(String name, Element e, Object... args);
}
