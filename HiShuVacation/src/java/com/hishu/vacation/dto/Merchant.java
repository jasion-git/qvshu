package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Merchant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -440991878252709177L;

	private Integer id;
	private String account;
	private String name;
	private Integer sex;
	private Integer provinceId;
	private Integer cityId;
	private Date updateTime;
	private Date createTime;
	private String cancelBookDes;//退改说明
	private Integer cancelBookType;//退改政策（1:不支持退改，2:支持随时退改，3:有条件退改）
	private String bookInstruction;
	private String bankRemark;
	private Integer bankSettlement;//结算方式（1:下单-周结算，2:下单-月结算，3:入住后 - 全款结算，4:定金+尾款结算，5:入住前 - 全款结算，6:入住 - 周结算，7:入住 - 月结算）
	private String bankAccount;
	private String bankSubBank;
	private String bankProvince;
	private String bankPhone;
	private String bankName;
	private Integer districtId;
	
	private String cityName;
	private String districtName;
	
	private String merchantContactList;
	private List<MerchantContact>merchantContacts;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
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
	public String getCancelBookDes() {
		return cancelBookDes;
	}
	public void setCancelBookDes(String cancelBookDes) {
		this.cancelBookDes = cancelBookDes;
	}
	public Integer getCancelBookType() {
		return cancelBookType;
	}
	public void setCancelBookType(Integer cancelBookType) {
		this.cancelBookType = cancelBookType;
	}
	public String getBookInstruction() {
		return bookInstruction;
	}
	public void setBookInstruction(String bookInstruction) {
		this.bookInstruction = bookInstruction;
	}
	public String getBankRemark() {
		return bankRemark;
	}
	public void setBankRemark(String bankRemark) {
		this.bankRemark = bankRemark;
	}
	public Integer getBankSettlement() {
		return bankSettlement;
	}
	public void setBankSettlement(Integer bankSettlement) {
		this.bankSettlement = bankSettlement;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankSubBank() {
		return bankSubBank;
	}
	public void setBankSubBank(String bankSubBank) {
		this.bankSubBank = bankSubBank;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankPhone() {
		return bankPhone;
	}
	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getMerchantContactList() {
		return merchantContactList;
	}
	public void setMerchantContactList(String merchantContactList) {
		this.merchantContactList = merchantContactList;
	}
	public List<MerchantContact> getMerchantContacts() {
		return merchantContacts;
	}
	public void setMerchantContacts(List<MerchantContact> merchantContacts) {
		this.merchantContacts = merchantContacts;
	}

}
