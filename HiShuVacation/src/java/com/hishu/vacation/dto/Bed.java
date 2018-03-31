package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

public class Bed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -834750545387485661L;

	private Integer id;
	private Integer houseId;
	private Double length;
	private Double width;
	private Integer count;
	private String mattress;
	private Date updateTime;
	private Date createTime;
	
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
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getMattress() {
		return mattress;
	}
	public void setMattress(String mattress) {
		this.mattress = mattress;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
