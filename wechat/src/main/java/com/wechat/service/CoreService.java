package com.wechat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.constants.Constants;
import com.wechat.extent.BaiduPlace;
import com.wechat.extent.UserLocation;
import com.wechat.menu.Button;
import com.wechat.menu.ClickButton;
import com.wechat.menu.ComplexButton;
import com.wechat.menu.Menu;
import com.wechat.menu.ViewButton;
import com.wechat.message.response.Article;
import com.wechat.message.response.NewsMessage;
import com.wechat.message.response.TextMessage;
import com.wechat.message.response.Token;
import com.wechat.util.BaiduMapUtil;
import com.wechat.util.CommonUtil;
import com.wechat.util.MenuUtil;
import com.wechat.util.MessageUtil;
import com.wechat.util.MySQLUtil;

/**
 * 核心服务类
 * @author gkl
 *
 */
public class CoreService {
	private static Logger log = LoggerFactory.getLogger(CoreService.class);
	private static String APPID = "wx3a75093b38b272f7";
	private static String APPSECRET = "3b6366b1586986d06776516775a8e18b";
	
	public String processRequest(HttpServletRequest request){
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
			
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(msgType);
			
			//文本消息
			if(msgType.equals(Constants.REQ_TEXT_MESSAGE)){
				String content = map.get("Content").trim();
				if(content.equals("附近")){
					respContent = getUsage();
				}else if(content.startsWith("附近")){
					String keyword = content.replaceAll("附近","");
					UserLocation location = MySQLUtil.getLastLocation(request, fromUserName);
					if(null == location){
						respContent = "未获取到用户位置！";
					}else{
						List<BaiduPlace> placeList = BaiduMapUtil.searchPlace(keyword, location.getBd09Lng(), location.getBd09Lat());
						if(null == placeList || 0 == placeList.size()){
							respContent = String.format("/难过,您发送的位置附近未搜索到\"%s\"信息！", keyword);
						}else{
							String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
							List<Article> articleList = BaiduMapUtil.makeArticleList(basePath,placeList, location.getBd09Lng(), location.getBd09Lat());
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setFromUserName(toUserName);
							newsMessage.setToUserName(fromUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(Constants.RESP_NEWS_MESSAGE);
							newsMessage.setArticles(articleList);
							newsMessage.setArticleCount(articleList.size());
							respXml = MessageUtil.messageToXml(newsMessage);
						}
					}
				}else{
					respContent = getUsage();
				}
				//TODO 处理文本消息请求
			}else if(msgType.equals(Constants.REQ_IMAGE_MESSAGE)){
				String picUrl = map.get("PicUrl");
				//TODO 处理图片消息请求
			}else if(msgType.equals(Constants.REQ_VOICE_MESSAGE)){
				String mediaId = map.get("MediaId");
				String format = map.get("Format");//语音格式:amr
				String recognition = map.get("Recognition");//语音识别结果
				//TODO 处理语音消息请求
			}else if(msgType.equals(Constants.REQ_VIDEO_MESSAGE)){
				String mediaId = map.get("MediaId");
				String thumbMediaId = map.get("ThumbMediaId");
				//TODO 处理视频消息请求
			}else if(msgType.equals(Constants.REQ_LOCATION_MESSAGE)){
				//用户发送的经纬度
				String lng = map.get("Location_X");
				String lat = map.get("Location_Y");
				String label = map.get("Label");
				String scale = map.get("Scale");
				//坐标转换后的经纬度
				String bd09Lng = null;
				String bd09Lat = null;
				UserLocation userLocation = BaiduMapUtil.convertCoord(lng, lat);
				if(null != userLocation){
					bd09Lng = userLocation.getBd09Lng();
					bd09Lat = userLocation.getBd09Lat();
				}
				//保存用户位置信息
				MySQLUtil.saveUserLocation(request, fromUserName, lng, lat, bd09Lng, bd09Lat);
				StringBuffer sb = new StringBuffer();
				sb.append("/愉快").append(" 本公成功接受您的位置！").append("\n\n");
				sb.append("您可以输入搜索关键词获取周边信息了").append("\n");
				sb.append("小贴士:必须以\"附近\"开头");
				respContent = sb.toString();
				//TODO 处理地理位置消息请求
			}else if(msgType.equals(Constants.REQ_LINK_MESSAGE)){
				String title = map.get("Title");
				String description = map.get("Description");
				String url = map.get("Url");
				//TODO 处理链接消息请求
			}else if(msgType.equals(Constants.REQ_EVENT)){//判断消息类型
				String eventType = map.get("Event");
				if(eventType.equals(Constants.CLICK_EVENT)){
					String eventKey = map.get("EventKey");
					//TODO 处理CLICK菜单返回内容
				}else if(eventType.equals(Constants.VIEW_EVENT)){
					String jumpUrl = map.get("EventKey");
					//TODO 处理VIEW菜单返回内容
				}else if(eventType.equals(Constants.SUBSCRIBE_EVENT)){
					respContent = getSubscribeMsg();
					MessageUtil.messageToXml(textMessage);
				}else if(eventType.equals(Constants.LOCATION_EVENT)){
					String latitude = map.get("Latitude");
					String longitude = map.get("Longitude");
					String precision = map.get("Precision");
					//TODO 处理用户地理事件
				}
			}
			if(null != respContent && !respContent.isEmpty()){
				textMessage.setContent(respContent);
				respXml = MessageUtil.messageToXml(textMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
	
	public void initMenu(){
		Token token = CommonUtil.getAccessToken(APPID, APPSECRET);
		if(token != null){
			String accessToken = token.getAccessToken();
			boolean flag = MenuUtil.createMenu(definedMenu(), accessToken);
			if(flag){
				log.info("初始化菜单成功！");
			}else{
				log.info("初始化菜单失败！");
			}
		}
	}
	
	public Menu definedMenu(){
		ClickButton btn1 = new ClickButton();
		btn1.setName("天气");
		btn1.setKey("b_weather");
		
		ViewButton btn2 = new ViewButton();
		
		ClickButton btn31 = new ClickButton();
		btn31.setName("赞一个");
		btn31.setKey("b_praise");
		
		ClickButton btn32 = new ClickButton();
		btn32.setName("热歌速递");
		btn32.setKey("b_hot_top");
		
		ClickButton btn33 = new ClickButton();
		btn33.setName("新歌驾到");
		btn33.setKey("b_new_top");
		
		ComplexButton btn3 = new ComplexButton();
		btn3.setName("音乐潮流榜");
		btn3.setSub_button(new Button[]{btn31,btn32,btn33});
		Menu menu = new Menu();
		menu.setButton(new Button[]{btn1,btn3});
		return menu;
	}
	
	/**
	 * 得到包含指定Unicode代码点的字符串
	 * @param codePoint
	 * @return
	 */
	private static String emoji(int codePoint){
		return String.valueOf(Character.toChars(codePoint));
	}
	
	/**
	 * 关注提示语
	 */
	private static String getSubscribeMsg(){
		StringBuffer sb = new StringBuffer();
		sb.append("您好，欢迎关注我是大歌星！").append("\n");
		sb.append("快乐的时候，来一首歌，它也许是你最想表达的心声").append("\n");
		sb.append("无聊的时候，来一首歌，它也许是你独处时最好的陪伴").append("\n");
		sb.append("悲伤的时候，来一首歌，它也许是你最好的安慰剂").append("\n\n");
		sb.append("本公为您提供了一些其他功能:").append("\n");
		sb.append("1) 周边搜索功能，回复\"附近\"即刻体验");
		sb.append("2) 天气预报功能，回复\"天气\"即刻了解");
		sb.append("3) 小游戏功能,暂时未开放/可怜");
		sb.append("4) 智能聊天 功能,暂时未开放/可怜");
		return sb.toString();
	}
	
	/**
	 * 使用说明
	 */
	private static String getUsage(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎使用周边搜索功能/愉快").append("\n\n");
		sb.append("1) 获取您的地理位置").append("\n");
		sb.append(" 点击窗口底部的'+'号,选择'位置',点击发送").append("\n");
		sb.append("2) 指定关键词搜索 ").append("\n");
		sb.append(" 格式：附近+关键词\n 例如：附近银行、附近酒店");
		return sb.toString();
	}
}
