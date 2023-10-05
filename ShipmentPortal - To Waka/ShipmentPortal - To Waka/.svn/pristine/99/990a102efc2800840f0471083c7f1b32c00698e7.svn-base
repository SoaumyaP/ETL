package com.csfe.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class StringUtil {
	public static boolean isNullOrBlank(String str) {
		return str == null || str.equals("");
	}
	
	/* Return null for empty string */
	public static String nullIfEmptyString(String str) {
		return str==null?null:(str.trim().length()==0?null:str.replaceAll("\\s*$", ""));
	}

	/* 
	 * Handle String which should be a code for fields
	 * Return null for empty string and return interned string if not null */
	public static String internCodeString(String str) {
		String tmp = nullIfEmptyString(str);
		return tmp==null?null:tmp.intern();
	}

	public static void appendToBuffer(StringBuffer buffer, String data, String separator){
		if(buffer == null) throw new IllegalArgumentException("Argument \"buffer\" cannot be null");
		if(data != null){
			if(separator!=null && buffer.length() > 0) buffer.append(separator);
			buffer.append(data);
		}
	}

	public static String replicate(String value, int times){
		StringBuffer buffer = new StringBuffer();

		if(times > 0){
			for(int i = 0 ; i < times ; i++){
				buffer.append(value);
			}
      		}
		return buffer.toString();
	}

	public static String substring(String original, int beginIndex, int endIndex){
		if(original==null) return null;
		int length = original.length();

		if(length<beginIndex) return null;
		if(length<endIndex) return original.substring(beginIndex);
		return nullIfEmptyString(original.substring(beginIndex, endIndex));
	}

	public static String convertFromEBCDICHexString(String hexString)
	   throws UnsupportedEncodingException{
		if(hexString==null) return null;
		if(hexString.length()%2==1) throw new IllegalArgumentException("Invaid hex string");

		char[] chars = new char[hexString.length()];
		byte[] convChars = new byte[hexString.length()/2];

		hexString.getChars(0, hexString.length(), chars, 0);
		for(int i=0;i<hexString.length();i+=2) {
			convChars[i/2] = (byte)Integer.parseInt(new String(chars, i, 2), 16);
		}
		return new String(convChars, "Cp1047");
	}
	/**
	 * break the value of damageDesc according to the first Chinese character.
	 * @param damageDesc
	 * @return a string 
	 */
	public static String breakDamageDesc(String damageDesc){
		if(damageDesc==null)return "";
		int j=0;
		byte[] bytes = damageDesc.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] < 0) {
				j = i;
				break;
			}
		}
		return damageDesc.substring(0, j)+"<br>"+damageDesc.substring(j);
	}
	
	
	public static String toMD5Deprecated(String str) throws NoSuchAlgorithmException {
		byte[] defaultBytes = str.getBytes();
		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.reset();
		algorithm.update(defaultBytes);
		byte messageDigest[] = algorithm.digest();
	            
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<messageDigest.length;i++) {
			hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
		}
		
		return hexString.toString();
	}
	
	public static String toMD5(String str) throws NoSuchAlgorithmException {
		byte[] defaultBytes = str.getBytes();
		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.reset();
		algorithm.update(defaultBytes);
		byte messageDigest[] = algorithm.digest();
	            
		StringBuffer hexString = new StringBuffer();
		String hex;
		for (int i=0;i<messageDigest.length;i++) {
			hex = Integer.toHexString(0xff & messageDigest[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
//			hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
		}
		
		return hexString.toString();
	}
	
	public static String getRandomString(int length) {
	   String randomStr = UUID.randomUUID().toString();
	   while(randomStr.length() < length) {
	       randomStr += UUID.randomUUID().toString();
	   }
	   return randomStr.substring(0, length);
	}
	
	public static String fillStr(String str, String fillStr, int totalDigit) {
		String retStr = StringUtil.nullIfEmptyString(str);
		
		for(int i=retStr.length(); i < totalDigit; i++) {
			retStr = fillStr + retStr;
		}
		
		return retStr;
	}
	
	public static String returnFilledStr(String str, String fillStr, int totalDigit) {
		String retStr = StringUtil.nullIfEmptyString(str);
		if(retStr == null || retStr.isEmpty()) return "";
		
		retStr = retStr.trim();
		while(!retStr.isEmpty() 
				&& (retStr.charAt(0)+"").equals(fillStr)) {
			retStr = retStr.substring(1, retStr.length());
		}
				
		return retStr;
	}
}
