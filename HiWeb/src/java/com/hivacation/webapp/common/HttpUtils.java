package com.hivacation.webapp.common;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class HttpUtils {

	protected static final Logger log=Logger.getLogger(HttpUtils.class);
	
	public static final String CHARSET_UTF_8="utf-8";
	private static PoolingHttpClientConnectionManager pool;
	private static RequestConfig requestConfig;
	static {
		try {
			SSLContextBuilder builder=new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
		    // 配置同时支持 HTTP 和 HTPPS
		    Registry<ConnectionSocketFactory>socketFactoryRegistry=RegistryBuilder
		    		.<ConnectionSocketFactory> create().register("http", 
		    				PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
		            		// 初始化连接管理器
		             		pool=new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		             		// 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
		             		pool.setMaxTotal(200);
		             		// 设置最大路由
		             		pool.setDefaultMaxPerRoute(2);
		             		// 根据默认超时限制初始化requestConfig
		             		int socketTimeout = 10000;
		             		int connectTimeout = 10000;
		             		int connectionRequestTimeout = 10000;
		             		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
		                    connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
		                    connectTimeout).build();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // 设置请求超时时间
		requestConfig=RequestConfig.custom().setSocketTimeout(50000).
				setConnectTimeout(50000).setConnectionRequestTimeout(50000).build();
	}
	
	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom()
             // 设置连接池管理
             .setConnectionManager(pool)
             // 设置请求配置
             .setDefaultRequestConfig(requestConfig)
             // 设置重试次数
             .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
             .build();
     
     return httpClient;
 }
	
	public static Map<String,Object>post(String url,Map<String,Object>param){
		Map<String,Object>ret=new HashMap<String,Object>();
		CloseableHttpResponse response=null;
		try{
			CloseableHttpClient httpClient=getHttpClient();
			HttpPost post=new HttpPost(url);
			
			List<NameValuePair> nvps=new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("username", "vip"));
	        nvps.add(new BasicNameValuePair("password", "secret"));
	        post.setEntity(new UrlEncodedFormEntity(nvps));
	        /*
	         * 表单方式
        	List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>(); 
        	pairList.add(new BasicNameValuePair("name", "admin"));
        	pairList.add(new BasicNameValuePair("pass", "123456"));
        	httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
	         * */
	        response=httpClient.execute(post);
	        HttpEntity entity=response.getEntity();
	        
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	        	String responseContent=EntityUtils.toString(entity, CHARSET_UTF_8);
	        	EntityUtils.consume(entity);
	        	if(StringUtils.isNotEmpty(responseContent)){
	        		JSONObject json=new JSONObject().parseObject(responseContent);
	        		
	        	}
	        	
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
                 // 释放资源
                 if (response != null) {
                     response.close();
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
		
		return ret;
	}
	
	public static String postJson(String url,JSONObject json){
		CloseableHttpResponse response=null;
		try{
			CloseableHttpClient httpClient=getHttpClient();
			HttpPost post=new HttpPost(url);
			StringEntity requestEntity=new StringEntity(json.toString(),"utf-8");//解决中文乱码问题   
			requestEntity.setContentEncoding("UTF-8");
			requestEntity.setContentType("application/json");
			post.setEntity(requestEntity);
			
	        response=httpClient.execute(post);
	        HttpEntity entity=response.getEntity();
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	        	String responseContent=EntityUtils.toString(entity, CHARSET_UTF_8);
	        	//log.info("返回response报文:"+responseContent);
	        	System.out.println("返回response报文:"+responseContent);
	        	EntityUtils.consume(entity);
	        	return responseContent;
	        }else{
	        	System.err.println("请求失败");
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
                 // 释放资源
                 if (response != null) {
                     response.close();
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
		return null;
	}
	
	public static String get(String url){
		log.info("请求url:"+url);
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse response=null;
		String responseContent=null;
		try {
			// 创建默认的httpClient实例.
	        httpClient=getHttpClient();
	        // 配置请求信息
	        HttpGet httpGet=new HttpGet(url);
	        httpGet.setConfig(requestConfig);
			// 执行请求
			response=httpClient.execute(httpGet);
			// 得到响应实例
			HttpEntity entity = response.getEntity();
			 
			// 判断响应状态
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
			    EntityUtils.consume(entity);
			    log.info("返回报文:"+responseContent);
			    return responseContent;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static String postXml(String url,String xml){
		CloseableHttpResponse response=null;
		try{
			CloseableHttpClient httpClient=getHttpClient();
			HttpPost post=new HttpPost(url);
			//设置参数
			post.setEntity(new StringEntity(xml,"utf-8"));
			
	        response=httpClient.execute(post);
	        HttpEntity entity=response.getEntity();
	        
	        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	        	String responseContent=EntityUtils.toString(entity, CHARSET_UTF_8);
	        	EntityUtils.consume(entity);
	        	log.info(responseContent);
	        	if(StringUtils.isNotEmpty(responseContent)){
	        		JSONObject json=new JSONObject().parseObject(responseContent);
	        	}
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
                 // 释放资源
                 if (response != null) {
                     response.close();
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
		
		return null;
	}
}
