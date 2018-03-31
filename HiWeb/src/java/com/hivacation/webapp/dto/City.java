package com.hivacation.webapp.dto;

import java.io.Serializable;

public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7437499188921613921L;

	private Integer id;
	private String name;
	private Integer hot;
	
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
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	
}
