package com.hishu.vacation.biz.file.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.hishu.vacation.biz.file.BaseFileService;
import com.hishu.vacation.biz.file.FileService;
import com.hishu.vacation.common.PropertiesUtils;
import com.hishu.vacation.exception.BizException;
import com.hishu.vacation.exception.ReturnCode;

public class LocalFileService extends BaseFileService implements FileService {

	private String root;
	private String web;
	
	public LocalFileService(){
		super();
		
		root=PropertiesUtils.getString("local.root");
		web=PropertiesUtils.getString("local.web");
		//生成文件根目录
		File f=new File(root);
		if(!f.exists()){
			f.mkdirs();
		}
	}
	
	@Override
	public String uploadFile(byte[] fileData, String fileName, String token)
			throws Exception {
		//校验token
		if(!chekToken(token)){
			throw new BizException(ReturnCode.ERROR_FILE_TRANS_TOKEN_ERROR,"文件传输token校验错误");
		}
		//校验文件类型，防止上传脚本文件
		String fileType=fileName.substring(fileName.lastIndexOf(".")+1);
		if(!checkFileType(fileType)){
			throw new BizException(ReturnCode.ERROR_FILE_TRANS_TYPE_ERROR,"传输文件类型错误");
		}
		//新生成文件
		File file=new File(root+File.separator+fileName);
		//判断文件的父文件夹是否存在
		File pFile=file.getParentFile();
		if(!pFile.exists()){
			pFile.mkdirs();
		}
		//生成文件
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileOutputStream fos=new FileOutputStream(file);
		BufferedOutputStream bos=new BufferedOutputStream(fos);
		bos.write(fileData);
		//关闭流
		if (bos != null) {
            try {
                bos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        
        String webUrl=web+"/"+fileName;
		return webUrl;
	}

	@Override
	public byte[] downloadFile(String fileName, String token) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeFile(String fileName, String token) throws Exception {
		//校验token
		if(!chekToken(token)){
			throw new BizException(ReturnCode.ERROR_FILE_TRANS_TOKEN_ERROR,"文件传输token校验错误");
		}
		//解析出fileName(/iov/common/getFile.file?_k=12125457878.jpg)
		if(StringUtils.isEmpty(fileName)){
			return;
		}
		String[]items=fileName.split("getFile.file?_k=");
		if(items.length!=2){
			return;
		}
		String filePath=items[1];//12125457878.jpg
		File file=new File(root+File.separator+filePath);
		if(file.isFile()&&file.exists()){
			file.delete();
		}
	}
	
	@Override
	public void removeFileGroup(String folderPath, String token) throws Exception {
		//校验token
		if(!chekToken(token)){
			throw new BizException(ReturnCode.ERROR_FILE_TRANS_TOKEN_ERROR,"文件传输token校验错误");
		}
		String path=root+File.separator+folderPath;
		File folder=new File(path);
		
		deleteDir(folder);
	}
	
	private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	
	public static void main(String[]args)throws Exception{
		/*File file=new File("D:/test001/001/002/text.txt");
		File pFile=file.getParentFile();
		if(!pFile.exists()){
			pFile.mkdirs();
		}
		//生成文件
		if(!file.exists()){
			file.createNewFile();
		}
		//写文件
		String s="hello world";
		byte[]fileData=s.getBytes();
		
		FileOutputStream fos=new FileOutputStream(file);
		BufferedOutputStream bos=new BufferedOutputStream(fos);
		bos.write(fileData);
		//关闭流
		if (bos != null) {
            try {
                bos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }*/
		
		LocalFileService t=new LocalFileService();
		//删除文件夹
		t.deleteDir(new File("D:/test001/001/002/"));
	}

	

}
