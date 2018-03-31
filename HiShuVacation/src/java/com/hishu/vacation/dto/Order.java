package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hishu.vacation.common.json.dateformat.YYYYMMddHHmmss;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2088385168784188198L;

	//0-有效，1-预定失败，2-已取消，3-等待退款，4-退款失败，5-已删除
	public static final int ORDER_STATUS_VALID=0;
	public static final int ORDER_STATUS_RESERVE_FAIL=1;
	public static final int ORDER_STATUS_CANCEL=2;
	public static final int ORDER_STATUS_WAIT_REFUND=3;
	public static final int ORDER_STATUS_REFUND_FAIL=4;
	public static final int ORDER_STATUS_DELETE=5;
	
	//付款状态（0-未支付，1-支付超时，2-支付失败，3-支付完成-确认中，4-支付成功）
	public static final int PAYMENT_STATUS_UNPAID=0;
	public static final int PAYMENT_STATUS_PAY_TIME_OUT=1;
	public static final int PAYMENT_STATUS_PAY_FAIL=2;
	public static final int PAYMENT_STATUS_PAID_CHECK=3;
	public static final int PAYMENT_STATUS_PAID=4;
	
	
	//入住状态（0-待入住，1-已入住，2-已退房）
	public static final int LIVE_STATUS_WAIT_FOR_LIVE=0;
	public static final int LIVE_STATUS_LIVE=1;
	public static final int LIVE_STATUS_LEAVE=2;
	
	//财务状态（0-未处理，已处理）
	public static final int FINANCIAL_STATUS_NO_HANDL=0;
	public static final int FINANCIAL_STATUS_HANDL=1;
	
	//房源类型
	public static final String HOUSE_TYPE_SYSTEM="system";
	public static final String HOUSE_TYPE_OTHER="other";
	
	private Integer id;
	private String orderNo;
	private Integer paymentStatus;
	private Integer orderStatus;
	private Integer liveStatus;
	private Integer financialStatus;
	private String type;
	private Integer houseId;
	private String houseType;
	private String userName;
	private Date updateTime;
	private Date createTime;
	private String remark;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date bookPaymentTime;
	private String userAccount;
	private Integer bookPaymentType;
	private Double bookGetAmount;//收款金额
	private Integer bookType;//预定类型（1-收全款，2-收定金）
	private Double bookDiscountAmount;//订单优惠
	private Double bookCollectionAmount;//代收金额
	private Double bookAmount;//订单总额
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date bookLeaveTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date bookLiveTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date bookTime;
	private String userPhone;
	private Integer houseDistrictId;
	private String houseDistrict;
	private String houseName;
	private Integer userId;
	private Integer houseBookCount;
	private Integer operatorId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@JsonSerialize(using=YYYYMMddHHmmss.class)
	public Date getBookPaymentTime() {
		return bookPaymentTime;
	}
	public void setBookPaymentTime(Date bookPaymentTime) {
		this.bookPaymentTime = bookPaymentTime;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public Integer getBookPaymentType() {
		return bookPaymentType;
	}
	public void setBookPaymentType(Integer bookPaymentType) {
		this.bookPaymentType = bookPaymentType;
	}
	public Double getBookGetAmount() {
		return bookGetAmount;
	}
	public void setBookGetAmount(Double bookGetAmount) {
		this.bookGetAmount = bookGetAmount;
	}
	public Integer getBookType() {
		return bookType;
	}
	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}
	public Double getBookDiscountAmount() {
		return bookDiscountAmount;
	}
	public void setBookDiscountAmount(Double bookDiscountAmount) {
		this.bookDiscountAmount = bookDiscountAmount;
	}
	public Double getBookCollectionAmount() {
		return bookCollectionAmount;
	}
	public void setBookCollectionAmount(Double bookCollectionAmount) {
		this.bookCollectionAmount = bookCollectionAmount;
	}
	public Double getBookAmount() {
		return bookAmount;
	}
	public void setBookAmount(Double bookAmount) {
		this.bookAmount = bookAmount;
	}
	@JsonSerialize(using=YYYYMMddHHmmss.class)
	public Date getBookLeaveTime() {
		return bookLeaveTime;
	}
	public void setBookLeaveTime(Date bookLeaveTime) {
		this.bookLeaveTime = bookLeaveTime;
	}
	@JsonSerialize(using=YYYYMMddHHmmss.class)
	public Date getBookLiveTime() {
		return bookLiveTime;
	}
	public void setBookLiveTime(Date bookLiveTime) {
		this.bookLiveTime = bookLiveTime;
	}
	public Date getBookTime() {
		return bookTime;
	}
	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public Integer getHouseDistrictId() {
		return houseDistrictId;
	}
	public void setHouseDistrictId(Integer houseDistrictId) {
		this.houseDistrictId = houseDistrictId;
	}
	public String getHouseDistrict() {
		return houseDistrict;
	}
	public void setHouseDistrict(String houseDistrict) {
		this.houseDistrict = houseDistrict;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getHouseBookCount() {
		return houseBookCount;
	}
	public void setHouseBookCount(Integer houseBookCount) {
		this.houseBookCount = houseBookCount;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getLiveStatus() {
		return liveStatus;
	}
	public void setLiveStatus(Integer liveStatus) {
		this.liveStatus = liveStatus;
	}
	public Integer getFinancialStatus() {
		return financialStatus;
	}
	public void setFinancialStatus(Integer financialStatus) {
		this.financialStatus = financialStatus;
	}

	
}
