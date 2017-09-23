package com.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.wechat.constants.Constants;
import com.wechat.message.response.Token;

public class CommonUtil {
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	public static Token getAccessToken(String appid,String appsecret){
		Token token = null;
		String requestUrl = Constants.ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject json = HttpUtil.doGet(requestUrl);
		//net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
		if(null != json){
			token = new Token();
			token.setAccessToken(json.getString("access_token"));
			token.setExpiresIn(json.getIntValue("expires_in"));
		}else{
			token = null;
			log.error("获取token失败 errcode:{} errmsg:{}",json.getIntValue("errcode"),json.getString("errmsg"));
		}
		return token;
	}
}
