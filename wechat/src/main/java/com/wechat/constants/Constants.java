package com.wechat.constants;

public class Constants {
	//接受消息类型
	public static String REQ_TEXT_MESSAGE = "text";
	public static String REQ_IMAGE_MESSAGE = "image";
	public static String REQ_VOICE_MESSAGE = "voice";
	public static String REQ_VIDEO_MESSAGE = "vodeo";
	public static String REQ_LOCATION_MESSAGE = "location";
	public static String REQ_LINK_MESSAGE = "link";
	
	//接受事件类型
	public static String SUBSCRIBE_EVENT = "subscribe";
	public static String UNSUBSCRIBE_EVENT = "unsubscribe";
	public static String SCAN_EVENT = "SCAN";
	public static String LOCATION_EVENT = "LOCATION";
	public static String CLICK_EVENT = "CLICK";
	public static String VIEW_EVENT = "VIEW";
	
	//回复消息类型
	public static String RESP_TEXT_MESSAGE = "text";
	public static String RESP_IMAGE_MESSAGE = "image";
	public static String RESP_VOICE_MESSAGE = "voice";
	public static String RESP_VIDEO_MESSAGE = "vodeo";
	public static String RESP_MUSIC_MESSAGE = "music";
	public static String RESP_NEWS_MESSAGE = "news";
	
	//获取access_token地址
	public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//媒体上传地址
	public static String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//菜单创建地址
	public static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"; 
	//菜单查询地址
	public static String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//菜单删除地址
	public static String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	//
	public static String SEND_CUSTOMER_SERVICE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
}
