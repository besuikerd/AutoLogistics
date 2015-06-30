package com.besuikerd.autologistics.client.gui.event;

import com.besuikerd.autologistics.client.gui.element.Element;
import com.besuikerd.autologistics.client.gui.element.ElementRootContainer;

public enum Trigger implements ITrigger{
	
	/**
	 * arguments: (Element e, int mouseX, int mouseY, which)
	 */
	PRESSED,
	
	/**
	 * arguments: (Element e, int mouseX, int mouseY, which)
	 */
	DOUBLE_PRESSED,
	
	/**
	 * arguments: (Element e, int mouseX, int mouseY, which)
	 */
	RELEASED,
	
	/**
	 * arguments: (Element e, int mouseX, int mouseY, amount)
	 */
	SCROLLED,
	
	/**
	 * arguments: (Element e, int mouseX, int mouseY)
	 */
	HOVER,
	
	/**
	 * arguments: (Element e, int mouseX, int mouseY, which)
	 */
	MOVE,
	
	/**
	 * arguments: (Element e)
	 */
	FOCUS,
	
	/**
	 * arguments: (Element e)
	 */
	FOCUSLOST,
	
	/**
	 * element is being updated
	 * arguments: (Element e)
	 */
	UPDATE,
	
	/**
	 * arguments: (Element e, char key, int code)
	 */
	KEY_PRESSED,
	
	/**
	 * arguments: (Element e, char key, int code)
	 */
	KEY_TYPED,
	
	/**
	 * arguments: (Element e, int code)
	 */
	KEY_RELEASED,
	
	/**
	 * element checked/unchecked
	 * arguments: (Element e, boolean checked)
	 */
	CHECKED,
	
	/**
	 * element toggled on/off
	 * arguments: (Element e, boolean toggled)
	 */
	TOGGLED,
	
	/**
	 * progress change
	 * arguments: (Element e, int oldProgress, int oldMax, int newProgress, int newMax)
	 */
	PROGRESS_CHANGED
	
	;

	
	@Override
	public void trigger(String name, ElementRootContainer root, Element e, Object... args) {
		root.getEventHandler().post(name, e, args);
	}
	
}
