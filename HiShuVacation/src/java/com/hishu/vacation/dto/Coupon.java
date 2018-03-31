package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hishu.vacation.common.json.dateformat.YYYYMMddHHmmss;

public class Coupon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6007225810884733943L;

	private Integer id;
	private String name;
	private String houseIds;
	private Integer packageId;
	private String descript;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date outOfDatetime;
	private Date updateTime;
	private Date createTime;
	private String limit;//使用限制 json数据
	
	private String packageName;//关联套餐名称

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHouseIds() {
		return houseIds;
	}

	public void setHouseIds(String houseIds) {
		this.houseIds = houseIds;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	@JsonSerialize(using=YYYYMMddHHmmss.class)
	public Date getOutOfDatetime() {
		return outOfDatetime;
	}

	public void setOutOfDatetime(Date outOfDatetime) {
		this.outOfDatetime = outOfDatetime;
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

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
