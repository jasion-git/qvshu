package com.hishu.vacation.biz.task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hishu.vacation.biz.service.HouseService;
import com.hishu.vacation.biz.service.OrderManagementService;
import com.hishu.vacation.dto.House;
import com.hishu.vacation.dto.HouseDayStatus;

@Component
public class GenHouseDayStatusTask {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private OrderManagementService orderManagementService;
	@Autowired
	private HouseService HouseService;
	
	//每天定时0点5分给系统所有房源添加固定的房态记录
	//@Scheduled(cron = "0 5 0 * * ?")
	public void execute(){
		log.info("每天生成房态信息");
		//找出系统所有的房源信息
		Map<String,Object>param=new HashMap<String,Object>();
		List<House>list=HouseService.getHouseList(param);
		Date date=new Date();
		if(list!=null&&!list.isEmpty()){
			for(House house:list){
				try{
					HouseDayStatus houseDayStatus=new HouseDayStatus();
					houseDayStatus.setHouseId(house.getId());
					houseDayStatus.setDate(date);
					houseDayStatus.setStatus(0);
					orderManagementService.addHouseDayStatus(houseDayStatus);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		//删除半年以前的房态数据
		HouseDayStatus houseStatus=new HouseDayStatus();
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, -6);
		Date createTime=c.getTime();
		houseStatus.setCreateTime(createTime);
		orderManagementService.deleteHouseDayStatusAfterHalfYear(houseStatus);
	}
}
