package com.wechat.util;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		if(null != json && !json.containsKey("errcode")){
			token = new Token();
			token.setAccessToken(json.getString("access_token"));
			token.setExpiresIn(json.getIntValue("expires_in"));
		}else{
			token = null;
			log.error("获取token失败 errcode:{} errmsg:{}",json.getIntValue("errcode"),json.getString("errmsg"));
		}
		return token;
	}
	
	public static String getFileExt(String contentType){
		String fileExt = "";
		if("image/jpeg".equals(contentType)){
			fileExt = ".jpg";
		}else if("audio/mpeg".equals(contentType)){
			fileExt = ".mp3";
		}else if("audio/amr".equals(contentType)){
			fileExt = ".amr";
		}else if("video/mp4".equals(contentType)){
			fileExt = ".mp4";
		}else if("video/mpeg4".equals(contentType)){
			fileExt = ".mp4";
		}
		return fileExt;
	}
	
	/**
	 * 时间转换
	 * @param createTime
	 * @return
	 */
	public String formatTime(String createTime){
		long time = Long.parseLong(createTime)*1000L;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}
}
