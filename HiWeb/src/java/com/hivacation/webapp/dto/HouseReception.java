package com.hivacation.webapp.dto;

import java.io.Serializable;
import java.util.Date;

public class HouseReception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4705616757206741759L;

	private Integer id;
	private String name;
	private String type;
	private Date createTime;
	private Date updateTime;
	
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
