package com.hivacation.webapp.webchat.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hivacation.webapp.common.CommonUtils;
import com.hivacation.webapp.common.HttpUtils;
import com.hivacation.webapp.common.MD5;

public class WeChatUtils {
	// 与接口配置信息中的Token要一致
    private static String token="test123";
    
    private static String PAY_MD5_KEY="192006250b4c09247ec02edce69f6a2d";

    /**
    * 方法名：checkSignature</br>
    * 详述：验证签名</br>
    * @param signature
    * @param timestamp
    * @param nonce
    * @return
    * @throws
     */
    public static boolean checkSignature(String signature, String timestamp,String nonce) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[] { token, timestamp, nonce };
        Arrays.sort(arr);
        
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        content = null;
        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
    * 方法名：byteToStr</br>
    * 详述：将字节数组转换为十六进制字符串</br>
    * @param byteArray
    * @return
    * @throws
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
    * 方法名：byteToHexStr</br>
    * 详述：将字节转换为十六进制字符串</br>
    * @param mByte
    * @return
    * @throws
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
    
    public static String getWechatUserInfo(String accessToken,String openId){
    	//https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID
    	String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId;
    	String ret=HttpUtils.get(url);
    	/*
    	 * {"subscribe":1,"openid":"o_DzIs27iqhhWxxgTpqS99XGxPnY","nickname":"Jasion","sex":1,"language":"zh_CN","city":"深圳","province":"广东","country":"中国","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/XuMnXGzNsnEQBQxAuQznvuJGk78BuQYo6QMTpL3cyOyJEzibw8TiaIDd61micdoNHvTjPWwJD2S8xwkib2iaRLa96Zm9Tjd8VgeAY\/0","subscribe_time":1506706029,"remark":"","groupid":0,"tagid_list":[]}
    	 * */
    	return ret;
    }
    
    public static String getWechatUserInfoForAuth2_0(String accessToken,String openId){
    	String url="https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
    	String ret=HttpUtils.get(url);
    	return ret;
    }
    
    public static String createQrcode(String accessToken,String sceneStr,Integer expireSeconds){
    	//URL: https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST
    	String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
    	/*
    	 * 	临时二维码
    	 * 	{"expire_seconds": 604800, "action_name": "QR_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
    	 * 	
    	 * 	永久二维码
    	 *  {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
    	 * */
    	JSONObject json=new JSONObject();
    	if(expireSeconds!=null){
    		//临时二维码
    		json.put("action_name", "QR_STR_SCENE");
    		json.put("expire_seconds", expireSeconds);
    		JSONObject actionInfo=new JSONObject();
    		json.put("action_info", actionInfo);
    		
    		JSONObject scene=new JSONObject();
    		actionInfo.put("scene", scene);
    		scene.put("scene_str", sceneStr);
    	}else{
    		json.put("action_name", "QR_LIMIT_STR_SCENE");
    		JSONObject actionInfo=new JSONObject();
    		json.put("action_info", actionInfo);
    		JSONObject scene=new JSONObject();
    		actionInfo.put("scene", scene);
    		scene.put("scene_str", sceneStr);
    	}
    	String ret=HttpUtils.postJson(url, json);
    	if(StringUtils.isNotEmpty(ret)){
    		JSONObject result=JSONObject.parseObject(ret);
    		/*
    		 * {
    		 * "ticket":"gQFK8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAybl91Q2NiWVliOFAxMDAwMGcwN0YAAgT21s9ZAwQAAAAA",
    		 * "url":"http:\/\/weixin.qq.com\/q\/02n_uCcbYYb8P10000g07F"
    		 * }
    		 * */
    		String ticket=result.getString("ticket");
    		try {
				String qrcodeUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(ticket, "utf-8");
				System.out.println("qrcodeUrl:"+qrcodeUrl);
				return qrcodeUrl;
    		} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
    	
    	return null;
    }
    
    public static String getPageAccessToken(String appId,String secret,String code){
    	// https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code 
    	String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
    	String ret=HttpUtils.get(url);
    	System.out.println("获取网页access token:"+ret);
    	return ret;
    }
    
    public static String getPageAccessTokenByRefreshToken(String appId,String refreshToken){
    	//https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
    	String url="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appId+"&grant_type=refresh_token&refresh_token="+refreshToken;
    	String ret=HttpUtils.get(url);
    	System.out.println("通过refresh token获取网页access token:"+ret);
    	return ret;
    }
    
    public static String getJsapiTicket(String accessToken){
    	//https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
    	String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
    	String ret=HttpUtils.get(url);
    	System.out.println("获取jsapi ticket:"+ret);
    	
    	return ret;
    }
    
    public static Map<String,Object> signJsTicket(String ticket,String url){
    	//jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
    	long timestampl=System.currentTimeMillis();
    	String timestamp=timestampl+"";
    	timestamp=timestamp.substring(0, 10);//只有10位
    	String nonceStr=CommonUtils.getRandomStr(16);
    	StringBuffer sb=new StringBuffer();
    	sb.append("jsapi_ticket=").append(ticket)
    		.append("&noncestr=").append(nonceStr)//随机数
    		.append("&timestamp=").append(""+timestamp)//时间戳
    		.append("&url=").append(url);
    	
    	System.out.println("jsapi签名前:"+sb.toString());
    	String signature=SHA1.getDigestOfString(sb.toString().getBytes());
    	signature=signature.toLowerCase();
    	System.out.println("jsapi签名后:"+signature);
    	Map<String,Object>ret=new HashMap<String,Object>();
    	ret.put("noncestr", nonceStr);
    	ret.put("timestamp", timestamp);
    	ret.put("signature", signature);
    	
    	return ret;
    }
    
    public static String signMD5ForPay(Map<String,String>param){
    	//第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
        //使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，
        //再将得到的字符串所有字符转换为大写，得到sign值signValue。
    	String str=formatUrlMap(param, false, false);
    	System.out.println(str);
    	//加上md5 key
    	str+="&key="+PAY_MD5_KEY;
    	System.out.println(str);
    	//String sign=MD5.encrypt2(str);
    	String sign=MD5.encrypt(str,1);
    	//转成大写
    	sign=sign.toUpperCase();
    	return sign;
    }
    
    /** 
     *  
     * 方法用途: 对所有传入参数按照字段名的Unicode码从小到大排序（字典序），并且生成url参数串<br> 
     * 实现步骤: <br> 
     *  
     * @param paraMap   要排序的Map对象 
     * @param urlEncode   是否需要URLENCODE 
     * @param keyToLower    是否需要将Key转换为全小写 
     *            true:key转化成小写，false:不转化 
     * @return 
     */  
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower){  
        String buff = "";  
        Map<String, String> tmpMap = paraMap;  
        try{  
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());  
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）  
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>(){  
   
                @Override  
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2){  
                    return (o1.getKey()).toString().compareTo(o2.getKey());  
                }  
            });  
            // 构造URL 键值对的格式  
            StringBuilder buf = new StringBuilder();  
            for (Map.Entry<String, String> item : infoIds){  
                if (StringUtils.isNotBlank(item.getKey())){  
                    String key = item.getKey();  
                    String val = item.getValue();  
                    if (urlEncode){  
                        val = URLEncoder.encode(val, "utf-8");  
                    }  
                    if (keyToLower){  
                        buf.append(key.toLowerCase() + "=" + val);  
                    } else{  
                        buf.append(key + "=" + val);  
                    }  
                    buf.append("&");  
                }  
   
            }  
            buff = buf.toString();  
            if (buff.isEmpty() == false){  
                buff = buff.substring(0, buff.length() - 1);  
            }  
        } catch (Exception e){  
           return null;  
        }  
        return buff;  
    }
    
    public static void main(String[]args){
    	/*String accessToken="Na674YDkpRUqRECaqIINlECKNAXD2cBmnfywhKioTD4rPrFmGJWOdjokn2g16gXqXyapbivad3AVj_QEOdi09eIq_1Rw0NhwO1howY8gAmvOdWuiMJ9ul68PZsXoJOQoLQRaAEASXY";
    	String sceneStr="kanjia";
    	Integer expireSeconds=null;
    	String ret=createQrcode(accessToken, sceneStr, expireSeconds);
    	System.out.println(ret);*/
    	
    	//signJsTicket("sM4AOVdWfPE4DxkXGEs8VF4hV3ormxB5Y32yuQW6rw6Wde4bA4HULOt03-vSi9vJFmN1dCFCloTULYiggREPzQ", "http://123.207.248.40/action/kanjia.html?p=1&nickName=Jasion&headImgUrl=http%3A%2F%2Fwx.qlogo.cn%2Fmmopen%2Fvi_32%2FQ0j4TwGTfTLa4jvf3VvkC8MuuPzoX4WupUqiaqhB0SPyTgkDlCRSDCuN3IarQRXglPDx5jcT24SSNd9SR88oZCQ%2F0");
    	
    	/*
    	 *  "appId":"wx2421b1c4370ec43b",     //公众号名称，由商户传入     
           "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数     
           "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串     
           "package":"prepay_id=u802345jgfjsdfgsdg888",     
           "signType":"MD5",         //微信签名方式：     
           "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
           
           
           stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
           
           99BF22B1755318215C56BD46ADEE9B74
           
           9A0A8659F005D6984697E2CA0A9CF3B7
    	 * */
    	Map<String,String>para=new HashMap<String,String>();
    	para.put("appId", "wxd930ea5d5a258f4f");
    	para.put("body", "test");
    	para.put("device_info", "1000");
    	para.put("mch_id", "10000100");
    	para.put("nonce_str", "ibuaiVcKdpRxkhJA");
    	
    	
    	System.out.println(signMD5ForPay(para));
    	//9A0A8659F005D6984697E2CA0A9CF3B7
    	
    }
}
