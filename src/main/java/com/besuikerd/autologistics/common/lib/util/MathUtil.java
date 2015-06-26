package com.besuikerd.autologistics.common.lib.util;

public class MathUtil {
    public static int max(int... values){
        int max = Integer.MIN_VALUE;
        for(int i : values){
            if(i > max){
                max = i;
            }
        }
        return max;
    }

    public static int min(int... values){
        int min = Integer.MAX_VALUE;
        for(int i : values){
            if(i < min){
                min = i;
            }
        }
        return min;
    }
}
