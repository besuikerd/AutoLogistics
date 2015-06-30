package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.element.adapter.IElementAdapter;
import com.besuikerd.autologistics.client.lib.gui.element.adapter.InvalidationListener;
import com.besuikerd.autologistics.client.lib.gui.layout.HorizontalLayout;
import com.besuikerd.autologistics.client.lib.gui.layout.LayoutDimension;
import com.besuikerd.autologistics.client.lib.gui.layout.Orientation;
import com.besuikerd.autologistics.client.lib.gui.layout.VerticalLayout;

public class ElementList extends ElementContainer implements InvalidationListener{
	protected IElementAdapter adapter;

	public ElementList(IElementAdapter adapter, Orientation orientation) {
		this.adapter = adapter;
		adapter.addInvalidationListener(this);
		this.layout = orientation.isVertical() ? new VerticalLayout() : new HorizontalLayout();
		invalidateList();
	}
	
	public ElementList(IElementAdapter adapter) {
		this(adapter, Orientation.VERTICAL);
	}

	/**
	 * call this when the given list has been changed
	 */
	public void invalidateList(){
		clear();
		for(int i = 0 ; i < adapter.getElementCount() ; i++){
			add(adapter.getElementAt(i));
		}
	}

	@Override
	public void onInvalidation() {
		invalidateList();
	}
}
