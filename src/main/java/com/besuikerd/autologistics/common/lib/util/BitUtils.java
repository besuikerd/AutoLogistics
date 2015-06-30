package com.besuikerd.autologistics.common.lib.util;

public class BitUtils {
	
	/**
	 * retrieves the n-th byte of the given integer
	 */
	public static byte getByte(int num, int n){
		return (byte) (num >> (8 * (4 - n - 1)) & 255);
	}
	
	/**
	 * checks if the nth bit is on
	 * @param b
	 * @param n
	 * @return
	 */
	public static boolean isOn(byte b, int n){
		return ((b >> (n - 1)) & 1) == 1;
	}
	
	public static byte toggleOn(byte b, int n){
		return (byte) (b | (1 << (n - 1)));
	}
	
	public static byte toggle(byte b, int n, boolean onOrOff){
		return onOrOff ? toggleOn(b, n) : toggleOff(b, n);
	}
	
	public static byte toggleOff(byte b, int n){
		return (byte) (b & ~(1 << (n - 1)));
	}
	
	public static int toggleOn(int i, int n){
		return (byte) (i | (1 << (n - 1)));
	}
	
	public static int toggleOff(int i, int n){
		return (byte) (i & ~(1 << (n - 1)));
	}
	
	public static int toggle(int i, int n, boolean onOrOff){
		return onOrOff ? toggleOn(i, n) : toggleOff(i, n);
	}
	
	/**
	 * converts the 8 lsb of the given integer to a String
	 * @param b
	 * @return the 8 lsb of the given integer as a String
	 */
	public static String ByteToString(int b){
		return Integer.toBinaryString((b & 0xff) | 0x100).substring(1);
	}
}
