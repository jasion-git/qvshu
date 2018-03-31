package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.Date;

public class HouseAttribute implements Serializable {

	/*属性类型（type_attr-类型属性，crowd_attr-符合人群，
	 */
	public static String TYPE_ATTR="attribute";
	public static String TYPE_CROWD="crowd";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7454411460045580694L;

	private Integer id;
	private String type;
	private String name;
	private String icon;
	private Date createTime;
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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

	

}
