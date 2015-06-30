package com.besuikerd.autologistics.client.lib.gui.element;

/**
 * delegates all callbacks towards a local container element, but is not an
 * instance of ElementContainer
 * 
 * @author Besuikerd
 * 
 */
public class ElementContainerDelegate extends Element {
	protected ElementContainer container;

	public ElementContainerDelegate(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public ElementContainerDelegate(int width, int height) {
		super(width, height);
	}

	@Override
	public void dimension() {
		container.dx = absX();
		container.dy = absY();
		container.dimension();
		this.width = container.width;
		this.height = container.height;
	}

	@Override
	public void draw() {
		container.draw();
	}

	@Override
	public void update() {
		container.update();
	}

	@Override
	public void onEvent(String name, Object[] args, Element e) {
		container.onEvent(name, args, e);
	}

	@Override
	protected boolean handleMouseInput(int mouseX, int mouseY) {
		return container.handleMouseInput(mouseX, mouseY);
	}

	@Override
	public boolean handleKeyboardInput() {
		return container.handleKeyboardInput();
	}
}
