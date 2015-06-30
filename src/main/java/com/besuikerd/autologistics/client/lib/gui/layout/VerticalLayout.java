package com.besuikerd.autologistics.client.lib.gui.layout;

import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.client.lib.gui.element.ElementContainer;

public class VerticalLayout implements Layout {
	private int xOffset;
	private int yOffset;

	/**
	 * maximum width of the elements in the rightmost column
	 */
	private int maxWidth;

	protected int marginX;
	protected int marginY;

	private boolean init;

	public VerticalLayout(int marginX, int marginY) {
		this.marginX = marginX;
		this.marginY = marginY;
	}

	public VerticalLayout() {
		this(0, 0);
	}

	public VerticalLayout marginX(int x) {
		this.marginX = x;
		return this;
	}

	public VerticalLayout marginY(int y) {
		this.marginY = y;
		return this;
	}

	@Override
	public void init(ElementContainer parent) {
		xOffset = 0;
		yOffset = 0;
		maxWidth = 0;
	}

	@Override
	public void layout(Element e, int index) {

		int elementWidth = e.getPaddedWidth();
		int elementHeight = e.getPaddedHeight();
		
		//check if element would fall out of vertical bounds
		if (e.getParent().getHeightDimension() != LayoutDimension.WRAP_CONTENT && yOffset + elementHeight > e.getParent().getHeight()) {
			yOffset = e.getParent().getPaddingTop();
			xOffset += e.getPaddedWidth() + marginX;
			maxWidth = e.getWidth();
		} else if (e.getWidth() > maxWidth) {
			maxWidth = elementWidth;
		}

		e.x(xOffset);
		e.y(yOffset);

		//increment xOffset
		yOffset += elementHeight + (e.getParent().getElementCount() - 1 == index ? 0 : marginY);
	}

	@Override
	public int getLaidOutWidth() {
		return xOffset + maxWidth;
	}
	
	@Override
	public int getLaidOutHeight() {
		return yOffset;
	}

	@Override
	public void align(Element e) {
		if (xOffset == 0) { //check if there is only a single column
			switch (e.getAlignment()) {
				case RIGHT:
					e.x(getLaidOutWidth() - e.getPaddedWidth());
					break;
				case CENTER:
					e.x((getLaidOutWidth() - e.getPaddedWidth()) / 2);
					break;
				default:
					break;
			}
		}
	}
}
