package com.hivacation.webapp.biz.dao;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.ThirdPartyProduct;

public interface ThirdPartyProductDao {

	public List<ThirdPartyProduct>getThirdPartyProductList(Map<String,Object>param);
}
