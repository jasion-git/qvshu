package com.hivacation.webapp.dto;

import java.io.Serializable;
import java.util.Date;

public class HouseStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -216332530128392430L;

	private Integer id;
	private Integer houseId;
	private Date date;
	private Integer status;
	private Integer orderId;

	private Double orderAmount;

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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	
}
