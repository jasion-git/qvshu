package com.hivacation.webapp.dto;

import java.io.Serializable;

public class ThirdPartyProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2736446169345552805L;
	
	private Integer id;
	private String name;
	private String type;
	private Integer districtId;
	private Double packagePrice;
	private Double singlePrice;
	private Integer supplierId;
	private String content;
	

	private String districtName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
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

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
