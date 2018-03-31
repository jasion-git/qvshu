package com.hishu.vacation.biz.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hishu.vacation.biz.service.AdminUserService;
import com.hishu.vacation.common.HttpClientUtils;
import com.hishu.vacation.dto.ThirdPartRoomStatus;

@Component
public class CaptureRoomStatusTask {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Scheduled(cron = "0 0/10 * * * ?")
	public void execute(){
		log.info("开始爬取第三方数据");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String,Map<String,Object>>rooms=new HashMap<String,Map<String,Object>>();
		
		//1.爬页面数据
		Map<String,String>param1=new HashMap<String,String>();
		param1.put("d", "ODYyODQ=");
		String html=HttpClientUtils.get("https://v.yunzhanggui.net/Public/roomthumb", param1);
		//log.info(html);
		if(StringUtils.isEmpty(html)){
			return;
		}
		
		//解析出html的元素
        Document document=Jsoup.parse(html);
        
        //房间的table
        Element roomstatusdetail1=document.getElementById("roomstatusdetail1");
        //循环出所有的房间
        List<Element>trList=roomstatusdetail1.getElementsByTag("tr");
        for(Element tr:trList){
        	System.out.println(tr.attr("rtid")+"="+tr.text());
        	Map<String,Object>room=new LinkedHashMap<String,Object>();
        	room.put("name", tr.text());
        	rooms.put(tr.attr("rtid"), room);
        }
		
        //2.爬api数据
        Map<String,String>param2=new HashMap<String,String>();
        param2.put("t", "860");
        param2.put("d", "ODYyODQ=");
        param2.put("watistics", "0");
        //时间是去当天的时间，跨度是一个月
        Calendar c=Calendar.getInstance();
        Date now=c.getTime();
        String fromdate=df.format(now);
        
        c.add(Calendar.MONTH, 1);
        Date endTime=c.getTime();
        String endDate=df.format(endTime);
        
        param2.put("fromdate", fromdate);
        param2.put("enddate", endDate);
        
        String jsonStr=HttpClientUtils.post("http://v.yunzhanggui.net/Public/getStatus", param2);
        //log.info(jsonStr);
        if(StringUtils.isEmpty(jsonStr)){
        	return;
        }
        JSONObject json=JSONObject.parseObject(jsonStr);
		
		JSONObject roomstatus=json.getJSONObject("roomstatus");
		for(Map.Entry<String, Map<String,Object>>entry:rooms.entrySet()){
			JSONObject status=roomstatus.getJSONObject(entry.getKey());
			Map<String,Object>room=entry.getValue();
			Set<Entry<String, Object>>map=status.entrySet();//Set<Entry<String, Object>>
			for(Entry<String, Object> p:map){
				room.put(p.getKey(), p.getValue());
			}
		}
		
		log.info(rooms);
		//保存到数据库
		String source="yunzhanggui";//房态来源
		List<ThirdPartRoomStatus>list=new ArrayList<ThirdPartRoomStatus>();
		for(Map.Entry<String, Map<String,Object>>entry:rooms.entrySet()){
			String thirdPartId=entry.getKey();
			Map<String,Object>room=entry.getValue();
			String roomName=(String) room.get("name");
			
			for(Map.Entry<String, Object>en:room.entrySet()){
				String k=en.getKey();
				Object v=en.getValue();
				if("name".equals(k)){
					continue;
				}
				
				ThirdPartRoomStatus roomStatus=new ThirdPartRoomStatus();
				roomStatus.setSource(source);
				roomStatus.setThirdPartId(thirdPartId);
				roomStatus.setRoomName(roomName);
				//日期
				try{
					Date date=df.parse(k);
					roomStatus.setDate(date);
					Integer status=Integer.parseInt(v.toString());
					roomStatus.setStatus(status);
				}catch(Exception e){
					log.error(e);
					e.printStackTrace();
					continue;
				}
				
				list.add(roomStatus);
			}
			
		}
		adminUserService.addThirdPartRoomStatus(list);
	}
	
	public static void main(String[]args){
		new CaptureRoomStatusTask().execute();
	}
}
