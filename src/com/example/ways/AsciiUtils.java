package com.example.ways;

public class AsciiUtils {
	
	/*****char&ascii*****/
	public static char ascii2Char(int ASCII) {
		return (char) ASCII;
	}

	public static int char2ASCII(char c) {
		return (int) c;
	}

	/*****string&ascii (Int型)*****/
	public static String ascii2String(int[] ASCIIs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ASCIIs.length; i++) {
			sb.append((char) ascii2Char(ASCIIs[i]));
		}
		return sb.toString();
	}

	/*****string&ascii*****/
	public static String ascii2String(String ASCIIs) {
		String[] ASCIIss = ASCIIs.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ASCIIss.length; i++) {
			sb.append((char) ascii2Char(Integer.parseInt(ASCIIss[i])));
		}
		return sb.toString();
	}

	public static int[] string2ASCII(String s) {// 字符串转换为ASCII码
		if (s == null || "".equals(s)) {
			return null;
		}

		char[] chars = s.toCharArray();
		int[] asciiArray = new int[chars.length];

		for (int i = 0; i < chars.length; i++) {
			asciiArray[i] = char2ASCII(chars[i]);
		}
		return asciiArray;
	}
}
