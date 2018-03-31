package com.hishu.vacation.biz.file;

public interface FileService {

	/**
	 * @param fileData
	 * @param fileName 例如：/001/002/test.jpg
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileData,String fileName,String token)throws Exception;
	public byte[] downloadFile(String fileName,String token)throws Exception;
	public void removeFile(String fileName,String token)throws Exception;
	public void removeFileGroup(String folder,String token)throws Exception;
}
