package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.List;

public class HouseDayStatusHouse1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7446531891919192302L;

	private Integer id;
	private String name;
	private Integer cityId;
	private Integer districtId;
	private Integer merchantId;
	private String cityName;
	private String districtName;
	private String merchantName;
	
	private List<HouseDayStatusHouse2> statusList;

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

	public List<HouseDayStatusHouse2> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<HouseDayStatusHouse2> statusList) {
		this.statusList = statusList;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	
}
