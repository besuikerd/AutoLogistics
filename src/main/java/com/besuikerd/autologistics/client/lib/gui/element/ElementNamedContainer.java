package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.client.lib.gui.layout.Alignment;

public class ElementNamedContainer extends ElementContainer{

	protected String name;
	
	public ElementNamedContainer(int width, int height, String name) {
		this(0, 0, width, height, name);
	}
	
	

	public ElementNamedContainer(int x, int y, int width, int height, String name) {
		super(x, y, width, height);
		this.name = name;
		add(new ElementLabel(name).align(Alignment.CENTER));
	}


	@Override
	public void draw() {
		super.draw();
	}
}
