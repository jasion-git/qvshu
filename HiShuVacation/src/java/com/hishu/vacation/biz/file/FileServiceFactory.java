package com.hishu.vacation.biz.file;

import com.hishu.vacation.biz.file.impl.LocalFileService;

public class FileServiceFactory {

	public static String TYPE_LOCAL="local";
	
	private static FileServiceFactory factory;
	
	private FileServiceFactory(){
		
	}
	
	public static FileServiceFactory getInstance(){
		if(factory==null){
			factory=new FileServiceFactory();
		}
		return factory;
	}
	
	public FileService getFileService(String type){
		FileService service=null;
		if(TYPE_LOCAL.equals(type)){
			service=new LocalFileService();
		}else{
			service=new LocalFileService();
		}
		
		return service;
	}
}
