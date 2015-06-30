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

    public static <E extends Comparable<E>> boolean inRange(E element, E min, E max) {
        int toMin = element.compareTo(min);
        int toMax = element.compareTo(max);
        return toMin == 0 || (toMin >= 0 && (toMax == 0 || (toMax <= 0)));
    }

    public static <E extends Comparable<E>> boolean inRange2D(E el1, E el2, E min, E max) {
        return inRange(el1, min, max) && inRange(el2, min, max);
    }

    public static <E extends Comparable<E>> boolean inRange2D(E el1, E min1, E max1, E el2, E min2, E max2) {
        return inRange(el1, min1, max1) && inRange(el2, min2, max2);
    }

    public static int sum(int... values) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum;
    }

    public static int product(int... values) {
        int product = 1;
        for (int value : values) {
            product *= value;
        }
        return product;
    }

    /**
     * aligns number between min and max (guarantees number stays within
     * inclusive bounds of min and max)
     *
     * @param min
     * @param number
     * @param max
     * @return
     */
    public static double align(double min, double number, double max) {
        return Math.min(max, Math.max(min, number));
    }

    public static int align(int min, int number, int max) {
        return Math.min(max, Math.max(min, number));
    }
}
