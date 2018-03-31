package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class House implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5572056279715214977L;
	
	private Integer id;
	private Integer merchantId;
	private Integer cityId;
	private Integer districtId;
	private String name;
	private String type;
	private Double price;
	private Date updateTime;
	private Date createTime;
	private String shareImgUrl;
	private String shareContent;
	private String shareTitle;
	private Integer funCount;
	private Integer liveCount;
	private String parking;
	private Integer isSpotCheck;
	private Integer toiletIndependen;
	private Integer toiletCommon;
	private Integer layoutBalcony;
	private Integer layoutKitchen;
	private Integer layoutOffice;
	private Integer layoutRoom;
	private Integer floorCount;
	private Double area;
	private Integer similarHouseCount;
	private String manageType;
	private String introduction;
	private String subIntroduction;
	private String offlineNo;
	private String thirdPartId;
	private String lawnChange;//草铺更换
	
	private String seoTitle;
	private String tagWords;
	private String keyWords;
	private String webDescription;
	
	private String checkInTime;
	private String checkOutTime;
	private String receptionTime;
	private Double deposit;
	private String remark;
	
	private String cityName;
	private String districtName;
	private String merchantName;
	private String houseImg;
	
	private List<HouseImg>houseImgList;
	private List<Bed>bedList;
	
	//辅助信息
	private String houseBeds;
	private String houseAttributes;
	private String houseFacilities;
	private String houseReceptions;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
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
	public String getShareImgUrl() {
		return shareImgUrl;
	}
	public void setShareImgUrl(String shareImgUrl) {
		this.shareImgUrl = shareImgUrl;
	}
	public String getShareContent() {
		return shareContent;
	}
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public Integer getFunCount() {
		return funCount;
	}
	public void setFunCount(Integer funCount) {
		this.funCount = funCount;
	}
	public Integer getLiveCount() {
		return liveCount;
	}
	public void setLiveCount(Integer liveCount) {
		this.liveCount = liveCount;
	}
	public String getParking() {
		return parking;
	}
	public void setParking(String parking) {
		this.parking = parking;
	}
	public Integer getIsSpotCheck() {
		return isSpotCheck;
	}
	public void setIsSpotCheck(Integer isSpotCheck) {
		this.isSpotCheck = isSpotCheck;
	}
	public Integer getToiletIndependen() {
		return toiletIndependen;
	}
	public void setToiletIndependen(Integer toiletIndependen) {
		this.toiletIndependen = toiletIndependen;
	}
	public Integer getToiletCommon() {
		return toiletCommon;
	}
	public void setToiletCommon(Integer toiletCommon) {
		this.toiletCommon = toiletCommon;
	}
	public Integer getLayoutBalcony() {
		return layoutBalcony;
	}
	public void setLayoutBalcony(Integer layoutBalcony) {
		this.layoutBalcony = layoutBalcony;
	}
	public Integer getLayoutKitchen() {
		return layoutKitchen;
	}
	public void setLayoutKitchen(Integer layoutKitchen) {
		this.layoutKitchen = layoutKitchen;
	}
	public Integer getLayoutOffice() {
		return layoutOffice;
	}
	public void setLayoutOffice(Integer layoutOffice) {
		this.layoutOffice = layoutOffice;
	}
	public Integer getLayoutRoom() {
		return layoutRoom;
	}
	public void setLayoutRoom(Integer layoutRoom) {
		this.layoutRoom = layoutRoom;
	}
	public Integer getFloorCount() {
		return floorCount;
	}
	public void setFloorCount(Integer floorCount) {
		this.floorCount = floorCount;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Integer getSimilarHouseCount() {
		return similarHouseCount;
	}
	public void setSimilarHouseCount(Integer similarHouseCount) {
		this.similarHouseCount = similarHouseCount;
	}
	public String getManageType() {
		return manageType;
	}
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getSubIntroduction() {
		return subIntroduction;
	}
	public void setSubIntroduction(String subIntroduction) {
		this.subIntroduction = subIntroduction;
	}
	public String getOfflineNo() {
		return offlineNo;
	}
	public void setOfflineNo(String offlineNo) {
		this.offlineNo = offlineNo;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getHouseImg() {
		return houseImg;
	}
	public void setHouseImg(String houseImg) {
		this.houseImg = houseImg;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public List<HouseImg> getHouseImgList() {
		return houseImgList;
	}
	public void setHouseImgList(List<HouseImg> houseImgList) {
		this.houseImgList = houseImgList;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getThirdPartId() {
		return thirdPartId;
	}
	public void setThirdPartId(String thirdPartId) {
		this.thirdPartId = thirdPartId;
	}
	public String getLawnChange() {
		return lawnChange;
	}
	public void setLawnChange(String lawnChange) {
		this.lawnChange = lawnChange;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getTagWords() {
		return tagWords;
	}
	public void setTagWords(String tagWords) {
		this.tagWords = tagWords;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getWebDescription() {
		return webDescription;
	}
	public void setWebDescription(String webDescription) {
		this.webDescription = webDescription;
	}
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public String getReceptionTime() {
		return receptionTime;
	}
	public void setReceptionTime(String receptionTime) {
		this.receptionTime = receptionTime;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHouseBeds() {
		return houseBeds;
	}
	public void setHouseBeds(String houseBeds) {
		this.houseBeds = houseBeds;
	}
	public String getHouseAttributes() {
		return houseAttributes;
	}
	public void setHouseAttributes(String houseAttributes) {
		this.houseAttributes = houseAttributes;
	}
	public String getHouseFacilities() {
		return houseFacilities;
	}
	public void setHouseFacilities(String houseFacilities) {
		this.houseFacilities = houseFacilities;
	}
	public String getHouseReceptions() {
		return houseReceptions;
	}
	public void setHouseReceptions(String houseReceptions) {
		this.houseReceptions = houseReceptions;
	}
	public List<Bed> getBedList() {
		return bedList;
	}
	public void setBedList(List<Bed> bedList) {
		this.bedList = bedList;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
