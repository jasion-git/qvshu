package com.hivacation.webapp.dto;

import java.io.Serializable;

public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7643381546537380958L;

	private Integer id;
	private String name;
	private String account;
	private String phone;
	private String password;
	private String headerUrl;
	private String wxOpenId;
	private Integer proxyUser1Id;
	private Integer proxyUser2Id;
	
	private Boolean clearProxyUser12;
	private Boolean clearProxyUser2;
	
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	public String getHeaderUrl() {
		return headerUrl;
	}
	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}
	public Integer getProxyUser1Id() {
		return proxyUser1Id;
	}
	public void setProxyUser1Id(Integer proxyUser1Id) {
		this.proxyUser1Id = proxyUser1Id;
	}
	public Integer getProxyUser2Id() {
		return proxyUser2Id;
	}
	public void setProxyUser2Id(Integer proxyUser2Id) {
		this.proxyUser2Id = proxyUser2Id;
	}
	public Boolean getClearProxyUser12() {
		return clearProxyUser12;
	}
	public void setClearProxyUser12(Boolean clearProxyUser12) {
		this.clearProxyUser12 = clearProxyUser12;
	}
	public Boolean getClearProxyUser2() {
		return clearProxyUser2;
	}
	public void setClearProxyUser2(Boolean clearProxyUser2) {
		this.clearProxyUser2 = clearProxyUser2;
	}
	
}
