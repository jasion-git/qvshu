package com.hishu.vacation.dto;

import java.io.Serializable;
import java.util.List;

public class CompParam<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2008173579214850088L;

	private List<T>list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
