package com.wechat.constants;

public class Constants {
	//接受消息类型
	public static String REQ_TEXT_MESSAGE = "text";
	public static String REQ_IMAGE_MESSAGE = "image";
	public static String REQ_VOICE_MESSAGE = "voice";
	public static String REQ_VIDEO_MESSAGE = "vodeo";
	public static String REQ_LOCATION_MESSAGE = "location";
	public static String REQ_LINK_MESSAGE = "link";
	public static String REQ_EVENT = "event";
	
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
	
	//发送客服消息
	public static String SEND_CUSTOMER_SERVICE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	//OAuth2授权获取code
	public static String AUTHORIZE_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	//OAuth2授权获取access_token
	public static String AUTHORIZE_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	//OAuth2刷新access_token
	public static String AUTHORIZE_REFRESH_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	//获取用户信息
	public static String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
}
