package com.besuikerd.autologistics.common.lib.util;

public class StringUtils {

    public static final String ASCII_PATTERN = "\\A\\p{ASCII}*\\z";

    public static boolean isASCII(char c){
        return c > 31 && c < 127;
    }

    public static boolean isASCII(String s){
        return s.matches(ASCII_PATTERN);
    }
}
