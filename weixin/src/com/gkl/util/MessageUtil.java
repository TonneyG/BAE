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
	 * �ı���ϢתΪxml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		//���ø�Ԫ�صı�ǩ
		xstream.alias("xml", textMessage.getClass());
		String content = xstream.toXML(textMessage);
		return content;	
	}
	
	/**
	 * ͼ����ϢתΪxml
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
		text.setContent("�����͵���Ϣ�ǣ�"+content);
		return textMessageToXml(text);
	}
	
	public static String initNewsMessage(String toUserName,String fromUserName){
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("��Ϊ˭�����ĸ�");
		news.setDescription("��æµ��������,�������Լ�һֱ��Ϊ�����ƴ�������еľ������׸��˹�������Ҫ���������Լ����ĵĶ��ף��һ��Լ�");
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
	 *	���˵� 
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ���Ĺ�ע���밴�ղ˵���ʾ���в�����\n\n");
		sb.append("1.��������\n");
		sb.append("2.��ҥ����\n");
		sb.append("3.��������\n");
		sb.append("�ظ�?�����˲˵���");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("�ܽ��ס��ֿ��ܡ�Ѧ֮ǫ");
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("����ա�����");
		return sb.toString();
	} 
	
	public static String thirdMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("Alan walker��BigBang");
		return sb.toString();
	} 
	
}
