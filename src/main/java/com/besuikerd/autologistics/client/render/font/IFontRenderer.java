package com.besuikerd.autologistics.client.render.font;

import net.minecraft.client.gui.Gui;

import java.awt.*;

public interface IFontRenderer {
    void renderStringWithShadow(Gui gui, String text, int x, int y);
    void renderString(Gui gui, String text, int x, int y);
    void renderChar(Gui gui, char character, int x, int y);

    void setColor(int color);
    int getColor();

    void setScale(float scaleX, float scaleY);
    float getScaleX();
    float getScaleY();

    void setHorizontalSpacing(int amount);
    int getHorizontalSpacing();

    void setVerticalSpacing(int amount);
    int getVerticalSpacing();

    int measureStringWidth(String s);
    int measureStringHeight(String s);
}
