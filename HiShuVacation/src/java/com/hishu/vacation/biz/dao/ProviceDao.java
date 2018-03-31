package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Provice;

public interface ProviceDao {

	public int addProvice(Provice provice);
	public int deleteProvice(Provice provice);
	public int updateProvice(Provice provice);
	public Provice getProvice(Provice provice);
	public List<Provice>getProviceList(Map<String,Object>param);
	public int getProviceListCount(Map<String,Object>param);
}
