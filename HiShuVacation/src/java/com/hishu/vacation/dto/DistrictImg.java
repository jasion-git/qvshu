package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

public class DistrictImg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1461525682606863540L;

	public static final String TYPE_COMMON="common";
	public static final String TYPE_PERIPHERAL="peripheral";
	
	private Integer id;
	private Integer districtId;
	private String type;
	private String url;
	private Date createTime;
	private Date updateTime;
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
