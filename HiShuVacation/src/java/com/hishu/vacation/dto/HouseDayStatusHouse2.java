package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hishu.vacation.common.json.dateformat.YYYYMMdd;

public class HouseDayStatusHouse2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7646830439758241052L;

	private Date date;
	private Integer status;
	private Double orderAmount;
	
	@JsonSerialize(using=YYYYMMdd.class)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	
}
