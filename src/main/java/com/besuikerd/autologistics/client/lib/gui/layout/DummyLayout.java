package com.besuikerd.autologistics.client.lib.gui.layout;

import java.awt.Dimension;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.client.lib.gui.element.ElementContainer;

public class DummyLayout implements Layout{

	private int maxWidth;
	private int maxHeight;
	
	@Override
	public void init(ElementContainer container) {
		maxWidth = 0;
		maxHeight = 0;
	}

	@Override
	public void layout(Element e, int index) {
		if(e.getWidth() > maxWidth){
			maxWidth = e.getWidth();
		}
		
		if(e.getHeight() > maxHeight){
			maxHeight = e.getHeight();
		}
	}

	@Override
	public int getLaidOutWidth() {
		return maxWidth;
	}
	
	@Override
	public int getLaidOutHeight() {
		return maxHeight;
	}
	
	@Override
	public void align(Element e) {
	}
	
}
