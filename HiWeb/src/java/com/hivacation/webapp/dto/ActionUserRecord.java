package com.hivacation.webapp.dto;

import java.io.Serializable;
import java.util.Date;

public class ActionUserRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4125347315712689229L;

	private Integer id;
	private Integer houseId;
	private Integer actionId;
	private String openId;
	private String friendOpenId;
	private Date createTime;
	private Date updateTime;
	private Double amount;
	
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
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getFriendOpenId() {
		return friendOpenId;
	}
	public void setFriendOpenId(String friendOpenId) {
		this.friendOpenId = friendOpenId;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
