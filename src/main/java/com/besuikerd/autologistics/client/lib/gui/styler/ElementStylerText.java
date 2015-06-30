package com.besuikerd.autologistics.client.lib.gui.styler;


import com.besuikerd.autologistics.client.lib.gui.element.Element;
import com.besuikerd.autologistics.client.lib.gui.layout.Alignment;

import net.minecraft.client.gui.FontRenderer;

/**
 * styler that 
 * @author Besuikerd
 *
 */
public class ElementStylerText implements IElementStyler{

	public static final int COLOR_ENABLED = 0xffffa0;
	public static final int COLOR_DISABLED = 0xa0a0a0;
	
	protected String text;
	protected Alignment alignment;
	
	public ElementStylerText(String text, Alignment alignment) {
		this.text = text;
		this.alignment = alignment;
	}

	public ElementStylerText(String text) {
		this(text, Alignment.CENTER);
	}

	@Override
	public void style(Element e) {
		FontRenderer fr = e.getFontRenderer();
		switch(alignment){
			case CENTER:
				fr.drawStringWithShadow(text, e.absX() + ((e.getWidth() - fr.getStringWidth(text)) / 2), e.absY() + ((e.getHeight() - fr.FONT_HEIGHT) / 2), e.isEnabled() ? COLOR_ENABLED : COLOR_DISABLED);
			default:
				//TODO implement these
				break;
		}
	}

}
