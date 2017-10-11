package com.gkl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gkl.vo.News;
import com.gkl.vo.NewsMessage;
import com.gkl.vo.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MessageUtil {
	public static String MESSAGE_TEXT = "text";
	public static String MESSAGE_NEWS = "news";
	public static String MESSAGE_IMAGE = "image";
	public static String MESSAGE_VOICE = "voice";
	public static String MESSAGE_VIDEO = "video";
	public static String MESSAGE_LINK = "link";
	public static String MESSAGE_LOCATION = "location";
	public static String MESSAGE_EVENT = "event";
	public static String MESSAGE_SUBSCRIBE = "subscribe";
	public static String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static String MESSAGE_CLICK = "CLICK";
	public static String MESSAGE_VIEW = "VIEW";
	
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception{
		InputStream in = null;
		Map<String,String> map = new HashMap<String,String>();
		try{
			SAXReader reader = new SAXReader();
			in = request.getInputStream();
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			for(Element e : list){
				map.put(e.getName(), e.getText());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			in.close();
		}
		return map;
	}
	
	/**
	 * 文本消息转为xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		//设置根元素的标签
		xstream.alias("xml", textMessage.getClass());
		String content = xstream.toXML(textMessage);
		return content;	
	}
	
	/**
	 * 图文消息转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String newMessageToXML(NewsMessage newsMessage){
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		String content = xstream.toXML(newsMessage);
		return content;
	}
	
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setCreateTime(new Date().getTime());
		text.setMsgType(MESSAGE_TEXT);
		text.setContent("您发送的消息是："+content);
		return textMessageToXml(text);
	}
	
	public static String initNewsMessage(String toUserName,String fromUserName){
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("不为谁而作的歌");
		news.setDescription("在忙碌的生活中,当我们自己一直在为梦想打拼，把所有的精力都献给了工作，不要忘了聆听自己内心的独白，找回自己");
		news.setPicUrl("http://d877d56a.ngrok.io/weixin/image/1.jpg");
		news.setUrl("https://y.qq.com/n/yqq/song/000tf24R0sBwSp.html?ADTAG=baiduald&play=1");
		newsList.add(news);
		
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);
		return newMessageToXML(newsMessage);
	}
	
	/**
	 *	主菜单 
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("1.流行音乐\n");
		sb.append("2.民谣音乐\n");
		sb.append("3.国外音乐\n");
		sb.append("回复?调出此菜单。");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("周杰伦、林俊杰、薛之谦");
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("李玉刚、周深");
		return sb.toString();
	} 
	
	public static String thirdMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("Alan walker、BigBang");
		return sb.toString();
	} 
	
}
