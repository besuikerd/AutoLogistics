package com.besuikerd.autologistics.client.render.font;

public class GlyphData{
    public final UVMapping2D[] uvMappings;
    public final int totalHeight;
    public final int lineHeight;

    public GlyphData(UVMapping2D[] uvMappings, int totalHeight, int lineHeight) {
        this.uvMappings = uvMappings;
        this.totalHeight = totalHeight;
        this.lineHeight = lineHeight;
    }
}