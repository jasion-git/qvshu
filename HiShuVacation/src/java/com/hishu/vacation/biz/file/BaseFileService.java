package com.hishu.vacation.biz.file;

import java.util.ArrayList;
import java.util.List;

public class BaseFileService {

	public static List<String>FILE_TYPE_LIST=new ArrayList<String>();
	static{
		FILE_TYPE_LIST.add("txt");
		FILE_TYPE_LIST.add("doc");
		FILE_TYPE_LIST.add("docx");
		FILE_TYPE_LIST.add("jpg");
		FILE_TYPE_LIST.add("jpge");
		FILE_TYPE_LIST.add("png");
		FILE_TYPE_LIST.add("bmp");
		FILE_TYPE_LIST.add("xls");
		FILE_TYPE_LIST.add("xlsx");
	}
	
	public boolean checkFileType(String fileType){
		if(FILE_TYPE_LIST.contains(fileType)){
			return true;
		}
		return false;
	}
	
	public boolean chekToken(String token){
		//校验token
		return true;
	}
}
