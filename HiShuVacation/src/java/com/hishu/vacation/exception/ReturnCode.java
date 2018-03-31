package com.hishu.vacation.exception;

public class ReturnCode {

	//公共异常是4位 1000-9999
	public static final int SYSTEM_DEFAULT_ERROR=1000;
	public static final int ERROR_IO=1001;
	public static final int ERROR_PARAM_IS_REQUIRED=1002;//参数必填
	public static final int ERROR_PARAM_MAXLEN_LIMIT=1003;//参数超出最大长度
	public static final int ERROR_PARAM_MINLEN_LIMIT=1004;//参数低于最小长度
	public static final int ERROR_PARAM_FORMAT_ERROR=1005;//参数格式错误
	public static final int ERROR_PARAM_MINVAL_LIMIT=1006;//参数低于最小值
	public static final int ERROR_PARAM_MAXVAL_LIMIT=1007;//参数高于最大值
	public static final int ERROR_USER_PASSWORD_WRONG=1008;//用户密码错误
	public static final int ERROR_USER_NO_PERMISSION=1009;//没有权限
	public static final int ERROR_USER_NO_LOGIN=1010;//用户未登录
	public static final int ERROR_FILE_TRANS_TOKEN_ERROR=1011;//文件服务器token校验错误
	public static final int ERROR_FILE_TRANS_TYPE_ERROR=1012;//文件类型错误
	
	//业务异常是6位 100000-999999
	public static final int ERROR_NO_HOUSE_STATUS=100000;//没有空的房态
	public static final int ERROR_ORDER_CAN_NOT_DELETE_FOR_PAID=100001;//订单已付款，不能删除
	public static final int ERROR_ORDER_CAN_NOT_DELETE_FOR_LEAVE_TIME=100002;//还在退房时间范围内不能删除
	public static final int ERROR_HOUSE_STATUS_REF_ORDER=100003;//房态还关联订单，不能标记为有房

}
