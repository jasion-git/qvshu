package com.hishu.vacation.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	public static String FORMAT_YYYYMMDDHHMMSS="yyyyMMddHHmmss";
	
	public static String date2String(Date date,String format){
		if(date==null){
			return "";
		}
		if(format==null){
			format=FORMAT_YYYYMMDDHHMMSS;
		}
		SimpleDateFormat df=new SimpleDateFormat(format);
		
		return df.format(date);
	}
}
