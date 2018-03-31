package com.hivacation.webapp.dto;

import java.io.Serializable;

public class HouseAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4844597330008737719L;

	private Integer id;
	private String type;
	private String name;
	private String icon;
	
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

}
