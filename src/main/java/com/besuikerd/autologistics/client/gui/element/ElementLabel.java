package com.besuikerd.autologistics.client.gui.element;


public class ElementLabel extends Element{

	protected String text;
	protected int color = 0xFF404040;
	
	public ElementLabel(int x, int y, String text) {
		super(x, y, 0, 0);
		this.width = fontRenderer.getStringWidth(text) + 2;
		this.height = fontRenderer.FONT_HEIGHT + 2;
		this.text = text;
	}
	
	public ElementLabel(String text) {
		this(0, 0, text);
	}

	@Override
	public void draw() {
		super.draw();
        fontRenderer.drawString(text, absX() + ((width - fontRenderer.getStringWidth(text)) / 2), absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), color);
	}
}
