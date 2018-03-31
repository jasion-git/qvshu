package com.hivacation.webapp.dto;

import java.io.Serializable;
import java.util.Date;

public class SubscribeRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7784481963841886604L;

	private Integer id;
	private String account;
	private String openId;
	private String beSubscribe;
	private String name;
	private String headUrl;
	private Integer isSubscribe;
	private Date createTime;
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getBeSubscribe() {
		return beSubscribe;
	}
	public void setBeSubscribe(String beSubscribe) {
		this.beSubscribe = beSubscribe;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
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
	public Integer getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(Integer isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	
}
