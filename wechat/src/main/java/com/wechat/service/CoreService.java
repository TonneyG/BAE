package com.wechat.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.constants.Constants;
import com.wechat.menu.Button;
import com.wechat.menu.ClickButton;
import com.wechat.menu.ComplexButton;
import com.wechat.menu.Menu;
import com.wechat.menu.ViewButton;
import com.wechat.message.response.TextMessage;
import com.wechat.message.response.Token;
import com.wechat.util.CommonUtil;
import com.wechat.util.MenuUtil;
import com.wechat.util.MessageUtil;

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
				String content = map.get("Content");
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
			}else if(msgType.equals(Constants.REQ_EVENT)){//判断消息类型
				String eventType = map.get("Event");
				if(eventType.equals(Constants.CLICK_EVENT)){
					String eventKey = map.get("EventKey");
					//TODO 处理CLICK菜单返回内容
				}else if(eventType.equals(Constants.VIEW_EVENT)){
					String jumpUrl = map.get("EventKey");
					//TODO 处理VIEW菜单返回内容
				}else if(eventType.equals(Constants.SUBSCRIBE_EVENT)){
					textMessage.setContent("您好，欢迎关注我是大歌星！我们致力于为每一位用户提供满意的体验！");
					MessageUtil.messageToXml(textMessage);
				}else if(eventType.equals(Constants.LOCATION_EVENT)){
					String latitude = map.get("Latitude");
					String longitude = map.get("Longitude");
					String precision = map.get("Precision");
					//TODO 处理用户地理事件
				}
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
}
