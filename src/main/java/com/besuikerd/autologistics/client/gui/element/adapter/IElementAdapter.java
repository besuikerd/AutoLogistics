package com.besuikerd.autologistics.client.gui.element.adapter;

import com.besuikerd.autologistics.client.gui.element.Element;

public interface IElementAdapter {
	public Element getElementAt(int index);
	public int getElementCount();
	public void addInvalidationListener(InvalidationListener listener);
	public void removeInvalidationListener(InvalidationListener listener);
}
