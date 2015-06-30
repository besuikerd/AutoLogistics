package com.besuikerd.autologistics.client.lib.gui.layout;

import java.awt.Dimension;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.client.lib.gui.element.ElementContainer;

public class HorizontalLayout implements Layout{

	private int xOffset;
	private int yOffset;
	
	/**
	 * maximum height of the elements in the lowest row
	 */
	private int maxHeight;
	
	
	protected int marginX;
	protected int marginY;
	
	public HorizontalLayout(int marginX, int marginY) {
		this.marginX = marginX;
		this.marginY = marginY;
		maxHeight = 0;
	}
	
	public HorizontalLayout(){
		this(0, 0);
	}
	
	public HorizontalLayout marginX(int x){
		this.marginX = x;
		return this;
	}
	
	public HorizontalLayout marginY(int y){
		this.marginY = y;
		return this;
	}
	
	@Override
	public void init(ElementContainer parent) {
		xOffset = 0;
		yOffset = 0;
		maxHeight = 0;
	}
	
	@Override
	public void layout(Element e, int index) {
		
		int elementWidth = e.getPaddedWidth();
		int elementHeight = e.getPaddedHeight();
		
		//check if element would fall out of horizontal bounds		
		if(e.getParent().getWidthDimension() != LayoutDimension.WRAP_CONTENT && xOffset + elementWidth > e.getParent().getWidth()){
			xOffset = 0;
			yOffset += maxHeight + marginY;
			maxHeight = elementHeight;
		} else if(elementHeight > maxHeight){
			maxHeight = elementHeight;
		}
		
		//layout element coordinates
		e.x(e.getPaddingLeft() + xOffset);
		e.y(e.getPaddingTop() + yOffset);
		
		//increment xOffset
		xOffset += elementWidth + (e.getParent().getElementCount() - 1 == index ? 0 : marginX);
	}
	
	@Override
	public int getLaidOutWidth() {
		return xOffset;
	}
	
	@Override
	public int getLaidOutHeight() {
		return yOffset + maxHeight;
	}
	
	@Override
	public void align(Element e) {
		if (yOffset == 0) { //check if there is only a single row
			switch (e.getAlignment()) {
				case BOTTOM:
					e.y(getLaidOutHeight() - e.getPaddedHeight());
					break;
				case CENTER:
					e.y((getLaidOutHeight() - e.getPaddedHeight()) / 2);
					break;
				default:
					break;
			}
		}
	}
}
