package com.hivacation.webapp.dto;

import java.io.Serializable;

public class OrderThirdPartyProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3272912108453678486L;
	
	private Integer id;
	private Integer productId;
	private String name;
	private Integer count;
	private Integer orderId;
	private Double packagePrice;
	private Double singlePrice;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Double getPackagePrice() {
		return packagePrice;
	}
	public void setPackagePrice(Double packagePrice) {
		this.packagePrice = packagePrice;
	}
	public Double getSinglePrice() {
		return singlePrice;
	}
	public void setSinglePrice(Double singlePrice) {
		this.singlePrice = singlePrice;
	}


}
