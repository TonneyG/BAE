package com.wechat.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.wechat.advance.account.WeixinQRCode;
import com.wechat.advance.account.WeixinUserInfo;
import com.wechat.advance.account.WeixinUserList;
import com.wechat.advance.media.WeixinMedia;
import com.wechat.constants.Constants;
import com.wechat.message.response.Token;

public class AccountUtil {
	private static String APPID_test = "wxb92d0e4892297c19";
	private static String APPSECRET_test = "104cde607373053e243623aa9b530fc0";
	private static Logger log = LoggerFactory.getLogger(AccountUtil.class);
	
	public static WeixinQRCode createTemporaryQRCode(String accessToken,int expireSeconds,int sceneId){
		WeixinQRCode weixinQRCode = null;
		String requestUrl = Constants.CREATE_QRCODE_TICKET_URL.replace("TOKEN", accessToken);
		String jsonMsg = "{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": }}}";
		JSONObject jsonObject = HttpUtil.doPost(requestUrl, String.format(jsonMsg, expireSeconds));
		if(null != jsonObject){
			if(!jsonObject.containsKey("errcode")){
				weixinQRCode = new WeixinQRCode();
				weixinQRCode.setTicket(jsonObject.getString("ticket"));
				weixinQRCode.setExpireSeconds(jsonObject.getIntValue("expire_seconds"));
				log.info("创建临时带参二维码成功 ticket:{} expire_seconds:{}",weixinQRCode.getTicket(),weixinQRCode.getExpireSeconds());
			}else{
				weixinQRCode = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("创建临时带参二维码失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return weixinQRCode;
	}
	
	public static String createPermanentQRCode(String accessToken,int sceneId){
		String ticket = null;
		String requestUrl = Constants.CREATE_QRCODE_TICKET_URL.replace("TOKEN", accessToken);
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		JSONObject jsonObject = HttpUtil.doPost(requestUrl, String.format(jsonMsg, sceneId));
		if(jsonObject!=null){
			if(!jsonObject.containsKey("errcode")){
				ticket = jsonObject.getString("ticket");
				log.info("创建永久带参数二维码成功 ticket:{}",ticket);
			}else{
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("创建永久带参数二维码失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return ticket;
	}
	
	public static String getQRCode(String ticket,String savePath){
		String filePath = null;
		String requestUrl = Constants.SHOW_QRCODE_URL.replace("TICKET", ticket);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			
			if(!savePath.endsWith("/")){
				savePath += "/";
			}
			//将ticket作为文件名
			filePath = savePath+ticket+".jpg";
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while((size=bis.read(buf)) != -1){
				fos.write(buf, 0, size);
			}
			fos.close();
			bis.close();
			conn.disconnect();
			log.info("根据ticket换取二维码成功, filePath="+filePath);
		} catch (Exception e) {
			filePath = null;
			log.error("根据ticket换取二维码失败: {}",e);
		}
		return filePath;
	}
	
	public static WeixinUserInfo getUserInfo(String accessToken,String openId){
		WeixinUserInfo weixinUserInfo = null;
		String requestUrl = Constants.GET_USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID",openId);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		if(null != jsonObject){
			if(!jsonObject.containsKey("errcode")){
				weixinUserInfo = new WeixinUserInfo();
				weixinUserInfo.setSubscribe(jsonObject.getIntValue("subscribe"));
				weixinUserInfo.setOpenid(jsonObject.getString("openid"));
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				weixinUserInfo.setSex(jsonObject.getIntValue("sex"));
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				weixinUserInfo.setCity(jsonObject.getString("city"));
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				weixinUserInfo.setUnionid(jsonObject.getString("unionid"));
				weixinUserInfo.setRemark(jsonObject.getString("remark"));
				weixinUserInfo.setGroupid(jsonObject.getString("groupid"));
				weixinUserInfo.setTagidList(jsonObject.getJSONArray("tagid_list").toJavaList(String.class));
			}else{
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户基本信息失败,errorCode: {},errorMsg: {}",errorCode,errorMsg);
			}
		}
		return weixinUserInfo;
	}
	
	public static WeixinUserList getUserList(String accessToken,String nextOpenId){
		WeixinUserList weixinUserList = null;
		if(null == nextOpenId){
			nextOpenId = "";
		}
		String requestUrl = Constants.GET_USERS_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		if(null != jsonObject){
			if(!jsonObject.containsKey("errcode")){
				weixinUserList = new WeixinUserList();
				weixinUserList.setTotal(jsonObject.getIntValue("total"));
				weixinUserList.setCount(jsonObject.getIntValue("count"));
				weixinUserList.setOpenIdList(jsonObject.getJSONArray("data").toJavaList(String.class));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
			}else{
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取关注者列表失败:errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return weixinUserList;
	}
	
	public static void main(String[] args) {
		try{
			Token token = CommonUtil.getAccessToken(APPID_test, APPSECRET_test);
			WeixinQRCode weixinQRCode = createTemporaryQRCode(token.getAccessToken(),1800,-1);
			//String ticket = createPermanentQRCode(token.getAccessToken(),123);
			System.out.println(weixinQRCode.getTicket());
			/*String json = "{\"tagid_list\":[128,2]}";
			JSONObject jsonObject = JSONObject.parseObject(json);
			Object[] objs = jsonObject.getJSONArray("tagid_list").toArray();
			List<String> list = jsonObject.getJSONArray("tagid_list").toJavaList(String.class);
			System.out.println(objs);
			System.out.println(list);*/
		}catch(net.sf.json.JSONException e){
			System.out.println("出错了");
		}
	}
	
	/*public static void main(String[] args) {
		Token token = CommonUtil.getAccessToken(APPID_test, APPSECRET_test);
		WeixinMedia weixinMedia = AdvanceUtil.uploadMedia(token.getAccessToken(),"image","http://localhost:8080/wechat/img/timg.jpg");
		System.out.println(token.getAccessToken());
		System.out.println(weixinMedia.getMediaId());
		System.out.println(weixinMedia.getType());
		System.out.println(weixinMedia.getCreatedAt());
		
		String path = AdvanceUtil.getMedia("-xHzMc2pkc9jPflxTIWNhnA02isN_sQ8tvMnlYR8wSJwhLKbvWAcVhQH75jjrdw8R0ZkPiTzfqQW92U6YWu5ekoB8A-poVgcKY47a3XRcH67Y9EDGcwUFm-TeGy3cqX_KBMhAGAPGR", "Ky3b9IjZJzjE7V4YeChC7sIVF_3j3Jbjg63MR7xV5hY43j4aJXrq0YNV8Wad0FbJ", "D:/");
		System.out.println(path);
	}*/
}
