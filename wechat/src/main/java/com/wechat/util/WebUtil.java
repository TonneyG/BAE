package com.wechat.util;

import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wechat.constants.Constants;
import com.wechat.message.request.BaseMessage;
import com.wechat.ouath2.SNSUserInfo;
import com.wechat.ouath2.WeixinOauth2Token;

public class WebUtil {
	private static Logger log = LoggerFactory.getLogger(WebUtil.class);
	/**
	 * 获取网页授权凭证
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
		WeixinOauth2Token wat = null;
		String requestUrl = Constants.AUTHORIZE_ACCESSTOKEN_URL.replace("APPID", appId).
				replace("SECRET", appSecret).replace("CODE", code);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		if(null != jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				wat = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;
	}
	
	/**
	 * 刷新网页授权凭证
	 * @param appId
	 * @param refreshToken
	 * @return
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId,String refreshToken){
		WeixinOauth2Token wat = null;
		String requestUrl = Constants.AUTHORIZE_REFRESH_ACCESSTOKEN_URL.
				replace("APPID", appId).replace("REFRESH_TOKEN", refreshToken);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		if(null != jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				wat = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;
	}
	
	public static SNSUserInfo getSNSUserInfo(String accessToken,String openId){
		SNSUserInfo snsUserInfo = null;
		String requestUrl = Constants.GET_USERINFO_URL.
				replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		if(null != jsonObject){
			try{
				snsUserInfo = new SNSUserInfo();
				snsUserInfo.setOpenid(jsonObject.getString("openid"));
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				snsUserInfo.setSex(jsonObject.getIntValue("sex"));
				snsUserInfo.setCountry(jsonObject.getString("country"));
				snsUserInfo.setProvince(jsonObject.getString("province"));
				snsUserInfo.setCity(jsonObject.getString("city"));
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				snsUserInfo.setPrivilegeList(JSON.parseArray(jsonObject.getString("privilege"), String.class));
			}catch(Exception e){
				snsUserInfo = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户信息失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return null;
	}
	
	public static String urlEncodeUTF8(String source){
		String result = "";
		try {
			result = URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		String a = null;
		System.out.println(a);
		String str = "{'name':'张三','age':'10'}";
		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(str);
		BaseMessage message = new BaseMessage();
		message.setFromUserName("张三");
		message.setMsgId(123);
		
		BaseMessage message2 = new BaseMessage();
		message2.setFromUserName("李四");
		message2.setMsgId(456);
		
		List<BaseMessage> list = new ArrayList<BaseMessage>();
		list.add(message);
		list.add(message2);
		
		System.out.println(json.toJSONString());
		System.out.println(JSONObject.toJSONString(message,SerializerFeature.WriteMapNullValue));
		System.out.println(JSON.toJSONString(list));
	}
}
