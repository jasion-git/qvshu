package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Bed;

public interface BedDao {

	public int addBed(Bed bed);
	public int deleteBed(Bed bed);
	public int bathDeleteBed(Bed bed);
	public int updateBed(Bed bed);
	public List<Bed>getBedList(Map<String,Object>param);
}
