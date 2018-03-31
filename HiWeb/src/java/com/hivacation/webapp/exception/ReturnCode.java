package com.hivacation.webapp.exception;

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
	public static final int ERROR_WECHAT_NOT_SET=1011;//配置文件中或者数据库中微信公众号没有设置
	
	public static final int ERROR_WECHAT_OPENID_INVALIDATE=1012;//微信openId无效
	
	public static final int ERROR_KANJIA_ACTION_HAD_KANJIA=1013;//用户已经执行过一次相同的砍价
	public static final int ERROR_KANJIA_ACTION_HAD_KANJIA_SELF=1014;//自己已经为自己砍了价，则不再为朋友砍价
	public static final int ERROR_KANJIA_ACTION_HAD_KANJIA_FRIEND=1015;//自己已经为该朋友砍了价，则不再为朋友砍价
	public static final int ERROR_KANJIA_ACTION_HAD_0=1016;//已经砍到0元
	
	public static final int ERROR_HOUSE_STATUS_EMPTY=100004;//房态信息为空
	public static final int ERROR_HOUSE_STATUS_HAD_BOOK=100005;//房源已有人预定
	public static final int ERROR_HOUSE_STATUS_ORDER_AMOUNT_MISSING=100006;//房态价格缺失
	public static final int ERROR_PAY_THIRD_PARTY_PRODUCT_IS_NOT_EXIST=100007;//购买的第三方产品不存在
	public static final int ERROR_ORDER_AMOUNT_NOT_EQUAL=100008;//订单金额不相等
	
	public static final int ERROR_CREATE_ORDER_REF_USER_FAIL=100009;//新建订单关联用户失败
	
	
	
	
	
	
	
	
	
}
