package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

public class HouseImg implements Serializable{

	public static String TYPE_COMMON="common";
	public static String TYPE_SHARE="share";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2737485719287893166L;

	private Integer id;
	private Integer houseId;
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
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
