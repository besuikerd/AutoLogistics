package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.layout.HorizontalLayout;
import com.besuikerd.autologistics.client.lib.gui.layout.LayoutDimension;
import com.besuikerd.autologistics.client.lib.gui.layout.Orientation;
import com.besuikerd.autologistics.client.lib.gui.layout.VerticalLayout;
import com.besuikerd.autologistics.client.lib.gui.styler.IElementStyler;

public class ElementScrollContainer extends ElementContainer{
	protected ElementViewport viewport;
	protected ElementScrollBar scrollBar;
	
	protected ElementContainer container;
	protected Orientation orientation;
	
	@Override
	protected boolean onScrolled(int x, int y, int amount) {
		return scrollBar.onScrolled(x, y, amount);
	}
	
	public ElementScrollContainer(int dimension, Orientation orientation, final ElementContainer container) {
		super(orientation == Orientation.VERTICAL ? LayoutDimension.WRAP_CONTENT : LayoutDimension.ABSOLUTE, orientation == Orientation.VERTICAL ? LayoutDimension.ABSOLUTE : LayoutDimension.WRAP_CONTENT);
		this.container = container;
		this.orientation = orientation;
		final boolean vertical = orientation == Orientation.VERTICAL;
		this.viewport = new ElementViewport(vertical ? dimension : dimension, vertical ? dimension : dimension	, container);
		
		if(vertical){
			this.height = dimension;
			this.layout = new HorizontalLayout();
			viewport.widthDimension = LayoutDimension.WRAP_CONTENT;
		} else{
			this.width = dimension;
			this.layout = new VerticalLayout();
			viewport.heightDimension = LayoutDimension.WRAP_CONTENT;
		}
		
		this.scrollBar = new ElementScrollBar(11, orientation){
			
			@Override
			public boolean onScrolled(int x, int y, int amount) {
				return setProgress(progress + (-0.25 * amount / (orientation == Orientation.VERTICAL ? container.height : container.width) ));
//					setProgress(progress + (-0.1 * (amount / 120))); //consume input if progress changed
			}
			
			@Override
			public int getScrollerSize() {
				return Math.min(orientation == Orientation.VERTICAL ? scrollBar.containerScroller.height : scrollBar.containerScroller.width, Math.max(10, orientation == Orientation.VERTICAL ? (int) (height * ((double) height / container.getHeight())) : (int) (width * ((double) width / container.getWidth()))));
			}
		};
		super.add(viewport);
		super.add(scrollBar);
	}
	
	public ElementScrollContainer(int dimension, Orientation orientation) {
		this(dimension, orientation, new ElementContainer().layout(orientation == Orientation.VERTICAL ? new VerticalLayout() : new HorizontalLayout()));
	}
	
	public ElementScrollContainer(int height, ElementContainer container){
		this(height, Orientation.VERTICAL, container);
	}
	
	public ElementScrollContainer(int height){
		this(height, Orientation.VERTICAL);
	}
	
	@Override
	public void dimension() {
		
		//reset progress to 0 if container is smaller than viewport
		if(orientation == Orientation.VERTICAL && container.height < height || orientation == Orientation.HORIZONTAL && container.width < width){
			scrollBar.setProgress(0);
		}
		
		if(orientation == Orientation.VERTICAL) {
			viewport.yOffset = (int) (-1 * (container.height - this.height) * scrollBar.progress);
		} else{
			viewport.xOffset = (int) (-1 * (container.width - this.width) * scrollBar.progress);
		}
		super.dimension();
		
	}
	
	@Override
	public ElementContainer add(Element... e) {
		container.add(e);
		return this;
	}
	
	@Override
	public void clear() {
		container.clear();
	}
	
	@Override
	public ElementContainer remove(Element e) {
		container.remove(e);
		return this;
	}
	
	@Override
	public ElementContainer remove(int index) {
		container.remove(index);
		return this;
	}

	@Override
	public int getElementCount() {
		return container.getElementCount();
	}
}
