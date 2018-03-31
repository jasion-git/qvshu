package com.hishu.vacation.exception;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;


public class BizException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5276415112933977516L;

	private String errorMsg;
	private int errorCode;
	
	public BizException(Exception e,int errorCode,String errorMsg){
		super();
		//列举可识别的异常
		if(e!=null){
			//列举可识别异常
			if(e instanceof IOException){
				//如果是IO异常
				this.errorCode=ReturnCode.ERROR_IO;
				this.errorMsg="网络异常";
			}else{
				if(StringUtils.isNotEmpty(errorMsg)){
					this.errorMsg=errorMsg;
				}else{
					this.errorMsg="系统异常";
				}
				this.errorCode=errorCode;
			}
		}else{
			if(StringUtils.isNotEmpty(errorMsg)){
				this.errorMsg=errorMsg;
			}else{
				this.errorMsg="系统异常";
			}
			this.errorCode=errorCode;
		}
	}
	
	public BizException(int errorCode,String errorMsg){
		super();
		if(StringUtils.isNotEmpty(errorMsg)){
			this.errorMsg=errorMsg;
		}else{
			this.errorMsg="系统异常";
		}
		this.errorCode=errorCode;
	}
	
	public BizException(){
		super();
		this.errorCode=ReturnCode.SYSTEM_DEFAULT_ERROR;
		this.errorMsg="系统异常";
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
