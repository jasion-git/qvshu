package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class District implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2496801029324724561L;

	private Integer id;
	private Integer cityId;
	private String code;
	private String name;
	private Double longitude;//经度
	private Double latitude;//纬度
	private String introduce;
	private String peripheral;
	private String keyWords;
	private String tagWords;
	private String seoTitle;
	private Date createTime;
	private Date updateTime;
	
	private List<DistrictImg>districtImgs;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getPeripheral() {
		return peripheral;
	}
	public void setPeripheral(String peripheral) {
		this.peripheral = peripheral;
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
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getTagWords() {
		return tagWords;
	}
	public void setTagWords(String tagWords) {
		this.tagWords = tagWords;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public List<DistrictImg> getDistrictImgs() {
		return districtImgs;
	}
	public void setDistrictImgs(List<DistrictImg> districtImgs) {
		this.districtImgs = districtImgs;
	}

}
