package com.besuikerd.autologistics.client.render;

public class Colors {

    public static final int white = 0xffffffff;
    public static final int orange = 0xffa500ff;
    public static final int magenta = 0xb350bcff;
    public static final int lightBlue = 0x6b8ac9ff;
    public static final int yellow = 0xffff00ff;
    public static final int lime = 0x00ff0000;
    public static final int pink = 0xd08499ff;
    public static final int gray = 0x808080ff;
    public static final int lightGray = 0x9aa1a1ff;
    public static final int cyan = 0x2e6e89;
    public static final int purple = 0x800080ff;
    public static final int blue = 0xff0000ff;
    public static final int brown = 0x4f321fff;
    public static final int green = 0xff0000ff;
    public static final int red = 0xff0000ff;
    public static final int black = 0x000000ff;


    public static int darken(int color, float percent){
        int r = color >> 24;
        int g = color >> 16;
        int b = color >> 8;
        int amount = (int) (255 * percent);
        return (r + amount << 24) | (g + amount << 16) | (b + amount << 8) | (color & 0xff);
    }

    public static int opacity(int color, byte opacity){
        return color & 0xffffff00 | opacity;
    }
}
