package com.wechat.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wechat.constants.Constants;
import com.wechat.message.response.Article;
import com.wechat.message.response.Music;
import com.wechat.message.response.MusicMessage;
import com.wechat.message.response.NewsMessage;
import com.wechat.message.response.TextMessage;
import com.wechat.message.response.VideoMessage;
import com.wechat.message.response.VoiceMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MessageUtil {
	private static Logger log = LoggerFactory.getLogger(MessageUtil.class);
	/**
	 * 解析微信服务器发来的xml消息体
	 */
	public static Map<String,String> parseXml(HttpServletRequest request) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		
		for(Element e : elementList){
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		inputStream = null;
		return map;
	}
	
	/**
	 * 使xstream支持cdata标签
	 */
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out){
				//对所有XML节点的转换都添加CDATA标记
				boolean cdata = true;
				@SuppressWarnings("unchecked")
				public void startNode(String name,Class clazz){
					super.startNode(name, clazz);
				}
				
				protected void writeText(QuickWriter writer,String text){
					if(cdata){
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else{
						writer.write(text);
					}
				}
			};
		}
	});
	
	public static String messageToXml(TextMessage textMessage){
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	public static String messageToXml(VoiceMessage voiceMessage){
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}
	
	public static String messageToXml(VideoMessage videoMessage){
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}
	
	public static String messageToXml(MusicMessage musicMessage){
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	
	public static String messageToXml(NewsMessage newsMessage){
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
	
	/*----------------------------------------------------------------------------*/
	
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
		String json = HttpUtil.doPost(requestUrl, jsonMsg);
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(null != jsonObject){
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errMsg");
			if(0 == errorCode){
				result = true;
				log.info("客服消息发送成功 errcode:{} errmsg:{}",errorCode,errorMsg);
			}else{
				log.error("客服消息发送失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return result;
	}
}
