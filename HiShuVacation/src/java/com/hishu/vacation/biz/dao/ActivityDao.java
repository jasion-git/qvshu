package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Activity;

public interface ActivityDao {

	public int addActivity(Activity activity);
	public int deleteActivity(Activity activity);
	public int updateActivity(Activity activity);
	public Activity getActivity(Activity activity);
	public List<Activity>getActivityList(Map<String,Object>param);
	public int getActivityListCount(Map<String,Object>param);
}
