package com.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.constants.Constants;
import com.wechat.ouath2.WeixinOauth2Token;

import net.sf.json.JSONObject;

public class WebUtil {
	private static Logger log = LoggerFactory.getLogger(WebUtil.class);
	/**
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
		WeixinOauth2Token wat = null;
		String requestUrl = Constants.AUTHORIZE_ACCESSTOKEN_URL.replace("APPID", appId).
				replace("SECRET", appSecret).replace("CODE", code);
		String result = HttpUtil.doGet(requestUrl);
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(null != jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;
	}
}
