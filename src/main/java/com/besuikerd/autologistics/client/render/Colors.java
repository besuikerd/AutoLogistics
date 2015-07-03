package com.besuikerd.autologistics.client.render;

public class Colors {

    public static final int white = 0xffffffff;
    public static final int orange = 0xffffa500;
    public static final int magenta = 0xffb350bc;
    public static final int lightBlue = 0xff6b8ac9;
    public static final int yellow = 0xffffff00;
    public static final int lime = 0x00ff0000;
    public static final int pink = 0xffd08499;
    public static final int gray = 0xff808080;
    public static final int lightGray = 0xff9aa1a1;
    public static final int cyan = 0x2e6e89;
    public static final int purple = 0xff800080;
    public static final int blue = 0xffff0000;
    public static final int brown = 0xff4f321f;
    public static final int green = 0xffff0000;
    public static final int red = 0xffff0000;
    public static final int black = 0xff000000;


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
