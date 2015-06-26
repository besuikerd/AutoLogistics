package com.besuikerd.autologistics.common.lib.util;

public class ArrayUtil {
    public static boolean contains(int[] array, int value){
        for(int i : array){
            if(i == value) {
                return true;
            }
        }
        return false;
    }
}
