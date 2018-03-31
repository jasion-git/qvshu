package com.hivacation.webapp.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hivacation.webapp.biz.dao.ActionUserRecordDao;
import com.hivacation.webapp.biz.dao.CityDao;
import com.hivacation.webapp.biz.dao.HouseDao;
import com.hivacation.webapp.biz.dao.SubscribeRecordDao;
import com.hivacation.webapp.biz.dao.WeChatGZHDao;
import com.hivacation.webapp.biz.redis.RedisDao;
import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.dto.ActionUserRecord;
import com.hivacation.webapp.dto.City;
import com.hivacation.webapp.dto.SubscribeRecord;
import com.hivacation.webapp.dto.WeChatGZH;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private WeChatGZHDao weChatGZHDao;
	@Autowired
	private SubscribeRecordDao subscribeRecordDao;
	@Autowired
	private RedisDao jedisDao;
	@Autowired
	private ActionUserRecordDao actionUserRecordDao;
	@Autowired
	private HouseDao houseDao;
	@Autowired
	private CityDao cityDao;
	
	@Override
	public WeChatGZH getWeChatGZH(WeChatGZH wechat) {
		return weChatGZHDao.getWeChatGZH(wechat);
	}

	@Override
	public int updateWeChatGZH(WeChatGZH wechat) {
		return weChatGZHDao.updateWeChatGZH(wechat);
	}

	@Override
	public int addSubscribeRecord(SubscribeRecord record) {
		return subscribeRecordDao.addSubscribeRecord(record);
	}

	@Override
	public int deleteSubscribeRecord(SubscribeRecord record) {
		return subscribeRecordDao.deleteSubscribeRecord(record);
	}
	
	@Override
	public int updateSubscrebeRecord(SubscribeRecord record) {
		return subscribeRecordDao.updateSubscribeRecord(record);
	}
	
	@Override
	public SubscribeRecord getSubscribeRecord(SubscribeRecord record) {
		return subscribeRecordDao.getSubscribeRecord(record);
	}

	@Override
	public void setStringInCache(String key, String value, Integer expire) {
		jedisDao.setData(key, value, expire);
	}

	@Override
	public String getStringInCache(String key) {
		return jedisDao.getStringData(key);
	}

	@Override
	public int addActionUserRecord(ActionUserRecord record) {
		return actionUserRecordDao.addActionUserRecord(record);
	}

	@Override
	public int deleteActionUserRecord(ActionUserRecord record) {
		return actionUserRecordDao.deleteActionUserRecord(record);
	}

	@Override
	public int updateActionUserRecord(ActionUserRecord record) {
		return actionUserRecordDao.updateActionUserRecord(record);
	}

	@Override
	public List<ActionUserRecord> getActionUserRecordList(
			Map<String, Object> param) {
		return actionUserRecordDao.getActionUserRecordList(param);
	}

	@Override
	public int getActionUserRecordListCount(Map<String, Object> param) {
		return actionUserRecordDao.getActionUserRecordListCount(param);
	}

	@Override
	public List<Map<String, Object>> getKanJiaFriendList(
			Map<String, Object> param) {
		return actionUserRecordDao.getKanJiaFriendList(param);
	}

	@Override
	public List<Map<String, Object>> getHouseListOfHot(Map<String, Object> param) {
		return houseDao.getHouseListOfHot(param);
	}

	@Override
	public List<City> getCityListOfHot(Map<String, Object> param) {
		return cityDao.getCityListOfHot(param);
	}

	@Override
	public List<Map<String, Object>> getHouseListOfCity(
			Map<String, Object> param) {
		return houseDao.getHouseListOfCity(param);
	}

	@Override
	public List<Map<String, Object>> getHouseListOfRecommended(
			Map<String, Object> param) {
		return houseDao.getHouseListOfRecommended(param);
	}

	

	


}
