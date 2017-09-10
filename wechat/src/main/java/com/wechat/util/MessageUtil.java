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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wechat.message.response.Article;
import com.wechat.message.response.MusicMessage;
import com.wechat.message.response.NewsMessage;
import com.wechat.message.response.TextMessage;
import com.wechat.message.response.VideoMessage;
import com.wechat.message.response.VoiceMessage;

public class MessageUtil {
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
}
