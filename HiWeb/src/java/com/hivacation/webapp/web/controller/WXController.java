package com.hivacation.webapp.web.controller;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hivacation.webapp.annotation.Log;
import com.hivacation.webapp.annotation.ValidateFiled;
import com.hivacation.webapp.annotation.ValidateGroup;
import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.common.CommonUtils;
import com.hivacation.webapp.common.Constant;
import com.hivacation.webapp.common.HttpUtils;
import com.hivacation.webapp.common.PropertiesUtils;
import com.hivacation.webapp.dto.JsonResult;
import com.hivacation.webapp.dto.SubscribeRecord;
import com.hivacation.webapp.dto.WeChatGZH;
import com.hivacation.webapp.exception.BizException;
import com.hivacation.webapp.exception.ReturnCode;
import com.hivacation.webapp.webchat.common.WeChatUtils;
import com.hivacation.webapp.webchat.msg.Msg;
import com.hivacation.webapp.webchat.msg.MsgFactory;
import com.hivacation.webapp.webchat.msg.MsgInterviewee;



@Controller
@RequestMapping("/wx")
public class WXController {

	protected final Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private CommonService commonService;
	
	/**
	 * 接通微信公众号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/checktoken.json", method={RequestMethod.GET,RequestMethod.POST})
	public void checktoken(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		if(isGet){
			/*
		    signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
			timestamp	时间戳
			nonce	随机数
			echostr	随机字符串
			 * */
			
			String signature=request.getParameter("signature");
			String timestamp=request.getParameter("timestamp");
			String nonce=request.getParameter("nonce");
			String echostr=request.getParameter("echostr");
			
			log.info("signature:"+signature+",timestamp:"+timestamp+",nonce:"+nonce+",echostr:"+echostr);
			if(StringUtils.isEmpty(signature)&&StringUtils.isEmpty(timestamp)){
				//丢失参数
				return;
			}
			PrintWriter out=response.getWriter();
	        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			//这个动作只有第一次验证微信公众号设置的url时使用
			if(StringUtils.isNotEmpty(signature)){
				if(WeChatUtils.checkSignature(signature, timestamp, nonce)) {
		            out.print(echostr);
		            
		            out.close();
		            out=null;
		        }
			}
		}else{
			// 进入POST聊天处理
			String ret=acceptMsg(request);
			PrintWriter out=response.getWriter();
			out.print(ret);
			
			out.close();
            out=null;
		}
	}
	
	private String acceptMsg(HttpServletRequest request) throws Exception{
		//先获取request中的xml报文
		ServletInputStream in=request.getInputStream();
		// 将流转换为字符串  
        StringBuilder xmlMsg=new StringBuilder();  
        byte[] b=new byte[4096];  
        for(int n;(n=in.read(b))!=-1;){
            xmlMsg.append(new String(b, 0, n, "UTF-8"));
        }
        //打印报文
        String xmlStr=xmlMsg.toString();
        log.info("request:"+xmlStr);
        Document document=DocumentHelper.parseText(xmlStr);
        //解析出msg
        Msg msg=MsgFactory.getInstance().parse(document);
        MsgInterviewee interview=new MsgInterviewee();
        Object ret=interview.accept(msg);
        
        if(ret!=null){
        	return (String)ret;
        }
        
		return "";
	}
	
	@RequestMapping(value="/gotoUrl.json", method={RequestMethod.GET,RequestMethod.POST})
	public void gotoUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//微信会回调这个地址
		
		//code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
		String code=request.getParameter("code");
		//我 传给微信的东西，微信回调时返回给我，其实是一个页面跳转的目的地址,路径以0分割
		String state=request.getParameter("state");
		log.info("code:"+code);
		log.info("state:"+state);
		
		String[]paths=state.split("0");
		
		WeChatGZH wechat=new WeChatGZH();
		String account=PropertiesUtils.getString("wechat.gzh");
		wechat.setAccount(account);
		wechat=commonService.getWeChatGZH(wechat);
		if(wechat==null){
			log.info("系统未配置公众号");
			response.setCharacterEncoding("utf-8");
			String ret="系统错误:"+ReturnCode.ERROR_WECHAT_NOT_SET;
			response.getWriter().write(ret);
			return;
		}
		
		//access_token和用户无关，只要没过期，都能用
		//获取access_token，每天限制调用2000次。 过期时间为2小时。
		String accessToken=null;
		String openId=null;
		//先获取缓存中的refresh token
		/*
		 * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，
		 * refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
		 * 主要是为了避免用户在段时间内，重新授权
		 * */
		String refreshToken=commonService.getStringInCache(Constant.REFRESH_TOKEN_KEY);
		if(StringUtils.isEmpty(refreshToken)){
			//如果缓存中没有了，则重新获取
			/*
			 * { "access_token":"ACCESS_TOKEN",    
				 "expires_in":7200,    
				 "refresh_token":"REFRESH_TOKEN",    
				 "openid":"OPENID",    
				 "scope":"SCOPE" } 
			 * */
			String accessTokenRet=WeChatUtils.getPageAccessToken(wechat.getAppId(), wechat.getSecret(), code);
			JSONObject accessTokenJson=JSONObject.parseObject(accessTokenRet);
			accessToken=accessTokenJson.getString("access_token");
			openId=accessTokenJson.getString("openid");
			//refresh token存入缓存 有效时间为30天,可以为29天23小时
			refreshToken=accessTokenJson.getString("refresh_token");
			int ex=(60*60*24*29)+(60*60*23);
			commonService.setStringInCache(Constant.REFRESH_TOKEN_KEY, refreshToken, ex);
		}else{
			//如果refresh token还存在，则直接用refresh token获取access token和openid
			String accessTokenRet=WeChatUtils.getPageAccessTokenByRefreshToken(wechat.getAppId(), refreshToken);
			JSONObject accessTokenJson=JSONObject.parseObject(accessTokenRet);
			accessToken=accessTokenJson.getString("access_token");
			openId=accessTokenJson.getString("openid");
		}
		
		String nickName="";
		String headImgUrl="";
		//根据openid和accessToken获取用户信息
		if(StringUtils.isNotEmpty(accessToken)&&StringUtils.isNotEmpty(openId)){
			String userJsonStr=WeChatUtils.getWechatUserInfoForAuth2_0(accessToken, openId);
			JSONObject userJson=JSONObject.parseObject(userJsonStr);
			if(userJson!=null){
				/*
				 * {    
				 *   "openid":" OPENID",  
					 " nickname": NICKNAME,   
					 "sex":"1",   
					 "province":"PROVINCE"   
					 "city":"CITY",   
					 "country":"COUNTRY",    
					 "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ
					4eMsv84eavHiaiceqxibJxCfHe/46",  
					"privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],    
					 "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL" 
					} 
				 * */
				nickName=userJson.getString("nickname");
				headImgUrl=userJson.getString("headimgurl");
				//先判断记录是否存在
				SubscribeRecord record=new SubscribeRecord();
				record.setOpenId(openId);
				record=commonService.getSubscribeRecord(record);
				if(record!=null){
					//已经存在，则更新
					SubscribeRecord recordUpdate=new SubscribeRecord();
					recordUpdate.setOpenId(openId);
					recordUpdate.setHeadUrl(headImgUrl);
					recordUpdate.setName(nickName);
					commonService.updateSubscrebeRecord(record);
				}else{
					//不存在，则插入
					SubscribeRecord recordAdd=new SubscribeRecord();
					recordAdd.setBeSubscribe(wechat.getAccount());
					recordAdd.setHeadUrl(headImgUrl);
					recordAdd.setName(nickName);
					recordAdd.setOpenId(openId);
					commonService.addSubscribeRecord(recordAdd);
				}
			}
		}
		
		String webUrl=PropertiesUtils.getString("web.url");
		String redirectUrl=webUrl;
		for(int i=0;i<paths.length;i++){
			String p=paths[i];
			if(i==(paths.length-1)){
				if(p.contains("PK")){
					//地址还携带了参数
					String[] its=p.split("PK");
					String pt=its[0];
					String paramKey=its[1];
					redirectUrl+="."+pt;
					//加上参数
					String param=commonService.getStringInCache(paramKey);
					if(StringUtils.isNotEmpty(param)){
						redirectUrl+="?"+param;
					}
				}else{
					redirectUrl+="."+p;//最后一个是格式，例如:.html,.jsp
				}
			}else{
				redirectUrl+="/"+p;
			}
		}
		//加上参数
		if(!redirectUrl.contains("?")){
			redirectUrl+="?p=1";
		}
		//加上openId
		redirectUrl+="&openId="+openId;
		
		if(StringUtils.isNotEmpty(nickName)){
			redirectUrl+="&nickName="+URLEncoder.encode(nickName, "utf-8");
		}
		if(StringUtils.isNotEmpty(headImgUrl)){
			redirectUrl+="&headImgUrl="+URLEncoder.encode(headImgUrl, "utf-8");
		}
		log.info("跳转地址:"+redirectUrl);
		//重定向
		response.sendRedirect(redirectUrl);
	}
	
	/**
	 * 主动刷新一次accessToken
	 * @param request
	 * @param response
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/refreshAccessToken.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult refreshAccessToken(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String account=PropertiesUtils.getString("wechat.gzh");
		
		WeChatGZH wechat=new WeChatGZH();
		wechat.setAccount(account);
		wechat=commonService.getWeChatGZH(wechat);
		if(wechat!=null){
			String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+wechat.getAppId()+"&secret="+wechat.getSecret();
			String ret=HttpUtils.get(url);
			JSONObject json=JSONObject.parseObject(ret);
			String accessToken=json.getString("access_token");
			if(StringUtils.isNotEmpty(accessToken)){
				log.info("获取到新的access token");
				WeChatGZH wechatUpdate=new WeChatGZH();
				wechatUpdate.setAccount(account);
				wechatUpdate.setAccessToken(accessToken);
				commonService.updateWeChatGZH(wechatUpdate);
			}
			result.setCode(0);
			result.setData(accessToken);
		}else{
			result.setCode(1);
			result.setMsg("获取accessToken失败");
		}
		
		return result;
	}
	
	@RequestMapping(value="/getWechatConfig.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getWechatConfig(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		result.setCode(JsonResult.CODE_FAIL);
		String url=request.getParameter("url");
		WeChatGZH wechat=new WeChatGZH();
		String account=PropertiesUtils.getString("wechat.gzh");
		wechat.setAccount(account);
		wechat=commonService.getWeChatGZH(wechat);
		if(wechat==null){
			log.info("系统未配置公众号");
			result.setCode(ReturnCode.ERROR_WECHAT_NOT_SET);
			result.setMsg("系统未配置公众号");
			return result;
		}
		//设置页面分享初始化参数
		String jsapiTicket=commonService.getStringInCache(Constant.JSAPI_TICKET_KEY);
		if(StringUtils.isEmpty(jsapiTicket)){
			//重新获取
			String jsapiJsonStr=WeChatUtils.getJsapiTicket(wechat.getAccessToken());
			JSONObject jsapiJson=JSONObject.parseObject(jsapiJsonStr);
			if(jsapiJson!=null){
				/*
				 * {
				"errcode":0,
				"errmsg":"ok",
				"ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
				"expires_in":7200
				}
				 * */
				jsapiTicket=jsapiJson.getString("ticket");
				//缓存ticket
				int exp=5400;//1.5小时
				commonService.setStringInCache(Constant.JSAPI_TICKET_KEY, jsapiTicket, exp);
			}
		}
		if(StringUtils.isNotEmpty(jsapiTicket)){
			Map<String,Object>ret=WeChatUtils.signJsTicket(jsapiTicket, url);
			/*String noncestr=(String) ret.get("noncestr");
			String timestamp=(String) ret.get("timestamp");
			String signature=(String) ret.get("signature");*/
			ret.put("appId", wechat.getAppId());
			result.setData(ret);
			result.setCode(JsonResult.CODE_SUCCESS);
		}
		
		return result;
	}
	
	@RequestMapping(value="/share.json", method={RequestMethod.GET,RequestMethod.POST})
	public void share(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//分享这个链接，然后跳转
		String target=request.getParameter("target");//action0kanjia0html
		String param="";
		String param1Name=request.getParameter("param1Name");
		String param1Value=request.getParameter("param1Value");
		if(StringUtils.isNotEmpty(param1Name)&&StringUtils.isNotEmpty(param1Value)){
			param+=param1Name+"="+param1Value;
		}
		String param2Name=request.getParameter("param2Name");
		String param2Value=request.getParameter("param2Value");
		if(StringUtils.isNotEmpty(param2Name)&&StringUtils.isNotEmpty(param2Value)){
			param+="&"+param2Name+"="+param2Value;
		}
		String param3Name=request.getParameter("param3Name");
		String param3Value=request.getParameter("param3Value");
		if(StringUtils.isNotEmpty(param3Name)&&StringUtils.isNotEmpty(param3Value)){
			param+="&"+param3Name+"="+param3Value;
		}
		if(StringUtils.isNotEmpty(param)){
			//解析出参数，并且缓存到redis
			String paramKey=CommonUtils.getRandomLetters(10);//随即10个字母
			commonService.setStringInCache(paramKey, param, 60*30);//缓存30分钟
			target+="PK"+paramKey;
		}
		
		String appId=PropertiesUtils.getString("wechat.appid");
		String webUrl=PropertiesUtils.getString("web.url");
		webUrl=URLEncoder.encode(webUrl, "utf-8");
		//重定向
		String redirectUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId
				+"&redirect_uri="+webUrl+"%2Fhiweb%2Fwx%2FgotoUrl.json&response_type=code&scope=snsapi_userinfo"
				+"&state="+target+"#wechat_redirect";
		response.sendRedirect(redirectUrl);
	}
	
	@RequestMapping(value="/createQrCode.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult createQrCode(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String content=request.getParameter("content");//为一个encode的字符串
		String action=request.getParameter("action");
		
		String key="tempCode_"+UUID.randomUUID().toString().replace("-", "");
		JSONObject json=new JSONObject();
		json.put("action", action);
		json.put("content", content);
		String value=json.toJSONString();
		//key="kj_"+key;
		commonService.setStringInCache(key, value, 60*60*2);
		
		//生成临时带参二维码
		WeChatGZH wechat=new WeChatGZH();
		String account=PropertiesUtils.getString("wechat.gzh");
		wechat.setAccount(account);
		wechat=commonService.getWeChatGZH(wechat);
		if(wechat==null){
			throw new BizException(ReturnCode.ERROR_WECHAT_NOT_SET,"未设置公众号");
		}
		String qrcodeUrl=WeChatUtils.createQrcode(wechat.getAccessToken(), key, 60*60*2);
		if(StringUtils.isNotEmpty(qrcodeUrl)){
			result.setData(qrcodeUrl);
			result.setCode(JsonResult.CODE_SUCCESS);
		}else{
			result.setCode(JsonResult.CODE_FAIL);
		}
		
		return result;
	}
	
	/**
	 * 微信预支付接口
	 * @param request
	 * @param response
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="amount"),
            @ValidateFiled(index=0,notNull=true,filedName="openId")
    })
	@Log
	@RequestMapping(value="/prePay.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult prePay(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Double amount=Double.parseDouble(request.getParameter("amount"));
		String openId=request.getParameter("openId");
		log.info(openId+"支付金额="+amount);
		
		//将金额转成单位分
		amount=amount*100;
		int totalFee=amount.intValue();
		
		//生成临时带参二维码
		WeChatGZH wechat=new WeChatGZH();
		String account=PropertiesUtils.getString("wechat.gzh");
		wechat.setAccount(account);
		wechat=commonService.getWeChatGZH(wechat);
		if(wechat==null){
			throw new BizException(ReturnCode.ERROR_WECHAT_NOT_SET,"未设置公众号");
		}
				
		//商户相关资料 
        String appid=wechat.getAppId();//公众号appid
        String appsecret=wechat.getSecret();//公众号秘钥
        String merchantId="";//商户号
        String partnerkey="";//商户API秘钥
		
        //生成随机数
        String randomStr=CommonUtils.getRandomStr(12);
        
        //生成一个系统订单 TODO
        String orderNo=UUID.randomUUID().toString().replace("-", "");
        
        //支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等：http://*/weChatpay/notifyServlet
        String notifyUrl ="http://域名/hiweb/wx/paysuccess.json";
        String body="产品描述";
        //附加数据
        String attach="xx科技有限公司";
        //订单生成的机器 IP
        String ip=request.getRemoteAddr();
        String tradeType="JSAPI";
        
        //获取sign（统一下单接口签名）
        //第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
        //使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，
        //再将得到的字符串所有字符转换为大写，得到sign值signValue。
        Map<String,String>param=new HashMap<String,String>();
        param.put("appid", appid);
        param.put("mch_id", merchantId);
        param.put("nonce_str", randomStr);
        param.put("body", body);
        param.put("attach", attach);
        param.put("out_trade_no", orderNo);
        param.put("total_fee", totalFee+"");
        param.put("spbill_create_ip", ip);
        param.put("notify_url", notifyUrl);
        param.put("trade_type", tradeType);
        param.put("openid", openId);
        String sign=WeChatUtils.signMD5ForPay(param);
        
        //统一下单接口携带参数（xml格式），接口地址https://api.mch.weixin.qq.com/pay/unifiedorder
        String xml="<xml>"+
                "<appid>"+appid+"</appid>"+
                "<mch_id>"+merchantId+"</mch_id>"+
                "<nonce_str>"+randomStr+"</nonce_str>"+
                "<sign>"+sign+"</sign>"+
                "<body><![CDATA["+body+"]]></body>"+
                "<attach>"+attach+"</attach>"+
                "<out_trade_no>"+orderNo+"</out_trade_no>"+
                //金额，这里写的1 分到时修改，测试用
//              "<total_fee>"+1+"</total_fee>"+
                "<total_fee>"+totalFee+"</total_fee>"+
                "<spbill_create_ip>"+ip+"</spbill_create_ip>"+
                "<notify_url>"+notifyUrl+"</notify_url>"+
                "<trade_type>"+tradeType+"</trade_type>"+
                "<openid>"+openId+"</openid>"+
                "</xml>";
        //发送请求
        String url="https://api.mch.weixin.qq.com/pay/unifiedorder";
        String ret=HttpUtils.postXml(url, xml);
        //返回也是xml文件
        //解析出prepay_id TODO
        String prepayId="";
        
        //生成H5调起微信支付API相关参数（前端页面js的配置参数）
        Map<String, String>finalpackage=new LinkedHashMap<String, String>();
        String timestamp=System.currentTimeMillis()+"";//当前时间的时间戳
        String packages="prepay_id="+prepayId;;//订单详情扩展字符串
        finalpackage.put("appId", appid);//公众号appid  
        finalpackage.put("timeStamp", timestamp);  
        finalpackage.put("nonceStr", randomStr); //随机数 
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");//签名方式
        //与统一支付的签名方式一致
        String finalsign=WeChatUtils.signMD5ForPay(finalpackage);//签名
        
        Map<String,Object>map=new HashMap<String,Object>();
        map.put("appId", appid);
        map.put("timeStamp", timestamp);
        map.put("nonceStr", randomStr);
        map.put("packages", packages);
        map.put("signType", "MD5");
        map.put("sign", finalsign);
		
        result.setCode(JsonResult.CODE_SUCCESS);
        result.setData(map);
		return result;
	}
	
	public static void main(String[]args)throws Exception{
		String xmlMsg="<xml><ToUserName><![CDATA[gh_52c1a5899c88]]></ToUserName>"
				+"<FromUserName><![CDATA[o_DzIs27iqhhWxxgTpqS99XGxPnY]]></FromUserName>"
				+"<CreateTime>1506525196</CreateTime>"
				+"<MsgType><![CDATA[text]]></MsgType>"
				+"<Content><![CDATA[你好]]></Content>"
				+"<MsgId>6470476447836073718</MsgId>"
				+"</xml>";
		
		String xmlStr=xmlMsg.toString();
        Document document=DocumentHelper.parseText(xmlStr);
        //解析出msg
        Msg msg=MsgFactory.getInstance().parse(document);
        MsgInterviewee interview=new MsgInterviewee();
        String ret=(String) interview.accept(msg);
        System.out.println(ret);
	}
	
}
