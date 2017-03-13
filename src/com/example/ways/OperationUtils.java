package com.example.ways;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationUtils {
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static String secretKey(){
		String resultStr = "";
		for (int i = 0; i < 16; i++) {
			int x = (int) (Math.random() * 95) + 32;
			char c = (char) x;
			resultStr += c;
		}
		return resultStr;
	}
	
	public static  String convertKey(String inStr,char c){

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ c);
		}
		String s = new String(a);
		return s;
	}
	
}
