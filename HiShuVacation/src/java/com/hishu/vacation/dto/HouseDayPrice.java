package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hishu.vacation.common.json.dateformat.YYYYMMdd;

public class HouseDayPrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8020496861677849497L;

	private Integer id;
	private Integer houseId;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date day;
	private String priceType;//价格类型（collection-代收款，commission-佣金）
	private Double collectionAmount;
	private Double orderAmount;
	private Double commissionRate;
	private Date updateTime;
	private Date createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	@JsonSerialize(using=YYYYMMdd.class)
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public Double getCollectionAmount() {
		return collectionAmount;
	}
	public void setCollectionAmount(Double collectionAmount) {
		this.collectionAmount = collectionAmount;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
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

}
