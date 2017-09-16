package com.wechat.util;

import java.util.List;

import javax.servlet.http.HttpUtils;

import com.wechat.constants.Constants;
import com.wechat.message.response.Article;
import com.wechat.message.response.Music;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CustomerServiceUtil {
	/**
	 * 组装文本客服消息
	 * @param openId
	 * @param content
	 * @return
	 */
	public static String makeTextCustomMessage(String openId,String content){
		content = content.replace("\"", "\\\"");
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\","
				+ "\"text\":{\"content\":\"%s\"}}";
		return String.format(jsonMsg, openId,content);
	}
	
	/**
	 * 组装图片客服消息
	 * @param openId
	 * @param mediaId
	 * @return
	 */
	public static String makeImageCustomMessage(String openId,String mediaId){
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\","
				+ "\"image\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId,mediaId);
	}
	
	/**
	 * 组装语音客服消息
	 * @param openId
	 * @param mediaId
	 * @return
	 */
	public static String makeVoiceCustomMessage(String openId,String mediaId){
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\","
				+ "\"voice\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId,mediaId);
	}
	
	/**
	 * 组装视频客服消息
	 * @param openId
	 * @param mediaId
	 * @param thumbMediaId
	 * @return
	 */
	public static String makeVideoCustomMessage(String openId,String mediaId,String thumbMediaId){
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\","
				+ "\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId,mediaId,thumbMediaId);
	}
	
	public static String makeMusicCustomMessage(String openId,Music music){
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}}";
		jsonMsg = String.format(jsonMsg, openId, JSONObject.fromObject(music).toString());
		jsonMsg = jsonMsg.replace("musicUrl", "musicurl");
		jsonMsg = jsonMsg.replace("HQMusicUrl", "hqmusicurl");
		jsonMsg = jsonMsg.replace("thumbMediaId", "thumb_media_id");
		return jsonMsg;
	}
	
	public static String makNewsCustomMessage(String openId,List<Article> articleList){
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
		String.format(jsonMsg, openId, JSONArray.fromObject(articleList).toString().replace("\"", "\\\""));
		jsonMsg = jsonMsg.replace("picUrl", "picurl");
		return jsonMsg;
	}
	
	public static boolean sendCustomMessage(String accessToken,String jsonMsg){
		boolean result = false;
		String requestUrl = Constants.SEND_CUSTOMER_SERVICE_URL.replace("ACCESS_TOKEN", accessToken);
		return result;
	}
}
