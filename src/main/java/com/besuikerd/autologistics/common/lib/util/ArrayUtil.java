package com.besuikerd.autologistics.common.lib.util;

import java.lang.reflect.Array;

public class ArrayUtil {
    public static boolean contains(int[] array, int value){
        for(int i : array){
            if(i == value) {
                return true;
            }
        }
        return false;
    }

    public static <A> boolean contains(A[] array, A value){
        for(A a : array){
            if(value.equals(a)){
                return true;
            }
        }
        return false;
    }

    public static int[] range(int start, int end){
        int[] result = new int[end - start + 1];
        for(int i = 0 ; i < result.length ; i++){
            result[i] = i + start;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] concat(E[] first, E[]... others){
        int totalSize = first.length;
        for(E[] array : others){
            totalSize += array.length;
        }
        E[] result = (E[]) Array.newInstance(first.getClass().getComponentType(), totalSize);

        System.arraycopy(first, 0, result, 0, first.length);

        int index = first.length;

        for(E[] array : others){
            System.arraycopy(array, 0, result, index, array.length);
            index += array.length;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] prepend(E el, E[] list){
        E[] result = (E[]) Array.newInstance(list.getClass().getComponentType(), list.length + 1);
        result[0] = el;
        System.arraycopy(list, 0, result, 1, list.length);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] append(E el, E[] list){
        E[] result = (E[]) Array.newInstance(list.getClass().getComponentType(), list.length + 1);
        result[result.length - 1] = el;
        System.arraycopy(list, 0, result, 0, list.length);
        return result;
    }
}
