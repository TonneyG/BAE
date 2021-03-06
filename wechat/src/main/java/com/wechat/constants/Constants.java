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
	//获取授权后用户信息
	public static String GET_SNSUSERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	//创建ticket(	POST:临时和永久)
	public static String CREATE_QRCODE_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	//通过ticket换取二维码(GET:TICKET记得进行UrlEncode )
	public static String SHOW_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	//获取用户基本信息
	public static String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//获取关注者列表(GET)
	public static String GET_USERS_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	//上传多媒体文件的接口(POST/FORM)
	public static String GET_MEDIAID_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//下载多媒体文件(GET)
	public static String DOWNLOAD_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	
	//百度地图Place API使用
	//place区域检索POI服务
	public static String SEARCH_POI_URL = "http://api.map.baidu.com/place/v2/search";
	//POI详细服务
	public static String POI_DETAIL_URL = "http://api.map.baidu.com/place/v2/detail";
	//百度坐标系转换
	/**
	 * 传递参数：
	 * x:经度
	 * y：纬度
	 * from：参数x、y的坐标系,0表示WGS-84坐标系,2表示GCJ-02坐标系
	 * to：目标坐标系,目前只支持百度坐标系,值为4
	 * mode：转换模式,值为1表示批量转换;值为空或其他值,表示单个转换
	 * 
	 * 返回参数：
	 * error：错误代码，0表示成功
	 * x：经Base64算法编码后的经度
	 * y：经Base64算法编码后的纬度
	 */
	public static String BAIDU_MAP_CONVERT_URL = "http://api.map.baidu.com/ag/coord/convert?x=X&y=Y&from=FROM&to=4&mode=MODE";
	
	/**
	 * coords:源坐标.格式:x1,y1;x2,y2
	 * ak:开发者密钥
	 */
	public static String BAIDU_MAP_CONVERT2_URL = "http://api.map.baidu.com/geoconv/v1/?coords=${COORDS}&from=${FROM}&to=${to}&ak=${ak}";
}
