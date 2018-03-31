package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

public class HouseFacility implements Serializable {

	/*
	 * 设施类型（entertainment-娱乐,outdoor-室外,indoor-室内,kitchen-厨房,clean-清洁）
	 * */
	public static String TYPE_ENTERTAINMENT="entertainment";
	public static String TYPE_OUTDOOR="outdoor";
	public static String TYPE_INDOOR="indoor";
	public static String TYPE_KITCHEN="kitchen";
	public static String TYPE_CLEAN="clean";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3943213833497507909L;

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
