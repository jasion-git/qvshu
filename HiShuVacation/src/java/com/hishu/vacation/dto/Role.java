package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2149842061445170765L;

	private Integer id;
	private String name;
	private String code;
	private Date createTime;
	private Date updateTime;
	
	private List<Module>modules;
	private List<Auth>auths;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public List<Auth> getAuths() {
		return auths;
	}
	public void setAuths(List<Auth> auths) {
		this.auths = auths;
	}

}
