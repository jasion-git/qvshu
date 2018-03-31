package com.hivacation.webapp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CouponPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4208683943180634403L;

	private Integer id;
	private String name;
	private String type;
	private String houseIds;
	private String content;
	private Integer count;
	private Date startTime;
	private Date endTime;
	private String condition;
	private String conditionValue;
	private Double discount;
	private Date createTime;
	private Date updateTime;
	
	private List<House>houses;
	
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
	public String getHouseIds() {
		return houseIds;
	}
	public void setHouseIds(String houseIds) {
		this.houseIds = houseIds;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
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
	public List<House> getHouses() {
		return houses;
	}
	public void setHouses(List<House> houses) {
		this.houses = houses;
	}

}
