package com.hivacation.webapp.common;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(encrypt("123456", 1));
	}

	public static String encrypt(String data,int n){
		String s=data;
		for(int i=0;i<n;i++){
			s=DigestUtils.md5Hex(s);
		}
		return s;
	}
	
	public static String encrypt2(String data){
		try{
			java.security.MessageDigest md=MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(data.getBytes("UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        for (byte item : array) {
	            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
	        }
	        return sb.toString().toUpperCase();
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
}
