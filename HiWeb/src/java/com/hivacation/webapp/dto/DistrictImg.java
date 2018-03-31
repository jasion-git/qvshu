package com.hivacation.webapp.dto;

import java.io.Serializable;

public class DistrictImg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -979463179116315964L;

	private Integer id;
	private Integer districtId;
	private String type;
	private String url;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
