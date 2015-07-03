package com.besuikerd.autologistics.client.lib.gui.styler;

import com.besuikerd.autologistics.client.lib.gui.element.Element;

public class ElementStylerColoredBorder implements IElementStyler{
    private int borderSize;
    private int color;

    public ElementStylerColoredBorder(int borderSize, int color) {
        this.borderSize = borderSize;
        this.color = color;
    }

    @Override
    public void style(Element e) {
        e.drawColoredBorder(e.getX(), e.getY(), e.getPaddedWidth(), e.getPaddedHeight(), borderSize, color);
    }
}
