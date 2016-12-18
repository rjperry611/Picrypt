package com.lezorte.picrypt.utils;

/**
 * Created by lezorte on 12/3/16.
 */
public class Debug {

	/**
	 * Prints "b" as a binary string with leading 0s
	 * 
	 * @param b
	 *            - the byte to print as binary
	 */
	public static void printBinary(byte b) {
		String bits = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
		System.out.println(bits.substring(0,4) + "_" + bits.substring(4,8));
	}

	/**
	 * Prints "i" as a binary string with leading 0s
	 * 
	 * @param num
	 *            - the integer to print as binary
	 */
	public static void printBinary(int num) {
		String bits = String.format("%32s", Integer.toBinaryString(num)).replace(' ', '0');
		for(int i=0;i<4;i++) {
			System.out.print(bits.substring(0+8*i, 4+8*i)+"_"+bits.substring(4+8*i,8+8*i)+(i<3?" ":""));
		}
		System.out.println();
		
	}

}