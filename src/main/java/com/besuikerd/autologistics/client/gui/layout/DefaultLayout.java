package com.besuikerd.autologistics.client.gui.layout;

import java.awt.Dimension;

import com.besuikerd.autologistics.client.gui.element.Element;
import com.besuikerd.autologistics.client.gui.element.ElementContainer;

public class DefaultLayout implements Layout{

	/**
	 * Maximum width found
	 */
	private int maxWidth;
	
	/**
	 * Maximum height found
	 */
	private int maxHeight;
	
	@Override
	public void init(ElementContainer container) {
		this.maxWidth = 0;
		this.maxHeight = 0;
	}
	
	
	@Override
	public void layout(Element e, int index) {
		int elementWidth = e.getPaddedWidth();
		int elementHeight = e.getPaddedHeight();
		
		if(e.getParent().getWidthDimension() == LayoutDimension.WRAP_CONTENT){
			e.x(Math.max(e.getX(), e.getPaddingLeft()));
		}
		
		if(e.getParent().getHeightDimension() == LayoutDimension.WRAP_CONTENT){
			e.y(Math.max(e.getY(), e.getPaddingTop()));
		}
		
		if(elementWidth > maxWidth){
			this.maxWidth = elementWidth;
		}
		
		if(elementHeight > maxHeight){
			this.maxHeight = elementHeight;
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
		switch (e.getAlignment()) {
			case TOP:
				e.y(0);
				e.x((e.getParent().getWidth() - e.getWidth()) / 2);
				
				break;
			case RIGHT:
				e.y((e.getParent().getHeight() - e.getHeight()) / 2);
				e.x(e.getParent().getWidth() - e.getPaddedWidth());
				break;
			case BOTTOM:
				e.y(e.getParent().getHeight() - e.getParent().getPaddingBottom() - e.getPaddingBottom());
				e.x((e.getParent().getWidth() - e.getWidth()) / 2);
				break;
			case LEFT:
				e.y((e.getParent().getHeight() - e.getHeight()) / 2);
				e.x(e.getParent().getPaddingLeft() + e.getPaddingLeft());
				break;
			case CENTER:
				e.y((e.getParent().getHeight() - e.getHeight()) / 2);
				e.x((e.getParent().getWidth() - e.getWidth()) / 2);
				break;
			default:
				break;
		}
		
	}
}
