package com.hivacation.webapp.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {

	public static int[]numbers={0,1,2,3,4,5,6,7,8,9};
	public static String[]letters={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
		"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	
	public static String getRequestIp(HttpServletRequest request){
		String ip=request.getHeader("x-forwarded-for");
		
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
		    ip=request.getHeader("Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
			ip=request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
			ip=request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
			ip=request.getRemoteAddr();
		}
		return ip;
	}

	public static Integer getBaseRedisDBNum() {
		
		return 0;
	}
	
	public static int getRandomNum(int min,int max){
		Random random=new Random();
        int s=random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	
	public static String getRandomStr(int length){
		List<String>list=new ArrayList<String>();
		for(int n:numbers){
			list.add(n+"");
		}
		for(String s:letters){
			list.add(s);
		}
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			int r=getRandomNum(0,list.size());
			sb.append(list.get(r));
		}
		return sb.toString();
	}
	
	public static String getRandomLetters(int length){
		List<String>list=new ArrayList<String>();
		for(String s:letters){
			list.add(s);
		}
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			int r=getRandomNum(0,list.size());
			sb.append(list.get(r));
		}
		return sb.toString();
	}
	
	public static void main(String[]args){
		for(int i=0;i<10;i++)
		System.out.println(getRandomStr(16));
	}
}
