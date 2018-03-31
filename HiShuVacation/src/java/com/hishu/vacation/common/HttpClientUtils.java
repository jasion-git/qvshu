package com.hishu.vacation.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

public class HttpClientUtils {

	protected static final Logger log=Logger.getLogger(HttpClientUtils.class);
	
	private static HttpClient httpclient=new DefaultHttpClient();
	
	private static String parseResponse(HttpResponse response)throws Exception{
		HttpEntity entity=response.getEntity();
	    BufferedReader is=new BufferedReader(new InputStreamReader(entity.getContent()));
	    StringBuffer sb=new StringBuffer();
        String line=null;
        while((line=is.readLine())!=null){  
        	String ss=new String(line.getBytes(),"UTF8");
        	sb.append(ss);
        }
        return sb.toString();
	}
	
	public static String get(String url,Map<String,String>param){
		try{
			if(param!=null&&!param.isEmpty()){
				if(!url.contains("?")){
					url+="?a=1";
				}
				for(Map.Entry<String, String>entry:param.entrySet()){
					url+="&"+entry.getKey()+"="+entry.getValue();
				}
			}
			log.info("请求Url="+url);
			HttpGet httpGet=new HttpGet(url);
			HttpResponse response=httpclient.execute(httpGet);
	        String body=parseResponse(response);
	        log.info("返回报文:"+body);
	        return body;
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	public static String post(String url,Map<String,String>param){
		try{
			HttpPost httpPost=new HttpPost(url);
			if(param!=null&&!param.isEmpty()){
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				for(Map.Entry<String, String>entry:param.entrySet()){
					params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			}
			log.info("请求Url="+url);
			log.info("请求参数="+param);
	        HttpResponse response=httpclient.execute(httpPost);
	        String body=parseResponse(response);
	        log.info("返回报文:"+body);
	        return body;
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}
}
