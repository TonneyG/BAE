package com.wechat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wechat.constants.Constants;
import com.wechat.util.MessageUtil;

/**
 * 核心服务类
 * @author gkl
 *
 */
public class CoreService {
	public static String processRequest(HttpServletRequest request){
		//XML格式的消息数据
		String respXml = "";
		//默认返回的文本消息内容
		String respContent = "未知的消息类型！";
		try {
			Map<String,String> map = MessageUtil.parseXml(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String msgId = map.get("MsgId");
			//文本消息
			if(msgType.equals(Constants.REQ_TEXT_MESSAGE)){
				String content = map.get("Content");
				//TODO 处理文本消息请求
			}else if(msgType.equals(Constants.REQ_IMAGE_MESSAGE)){
				String picUrl = map.get("PicUrl");
				//TODO 处理图片消息请求
			}else if(msgType.equals(Constants.REQ_VOICE_MESSAGE)){
				String mediaId = map.get("MediaId");
				String format = map.get("Format");
				//TODO 处理语音消息请求
			}else if(msgType.equals(Constants.REQ_VIDEO_MESSAGE)){
				String mediaId = map.get("MediaId");
				String thumbMediaId = map.get("ThumbMediaId");
				//TODO 处理视频消息请求
			}else if(msgType.equals(Constants.REQ_LOCATION_MESSAGE)){
				String lat = map.get("Location_X");
				String lon = map.get("Location_Y");
				String label = map.get("Label");
				String scale = map.get("Scale");
				//TODO 处理地理位置消息请求
			}else if(msgType.equals(Constants.REQ_LINK_MESSAGE)){
				String title = map.get("Title");
				String description = map.get("Description");
				String url = map.get("Url");
				//TODO 处理链接消息请求
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
