package com.wechat.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wechat.advance.media.WeixinMedia;
import com.wechat.advance.ouath2.SNSUserInfo;
import com.wechat.advance.ouath2.WeixinOauth2Token;
import com.wechat.constants.Constants;
import com.wechat.message.request.BaseMessage;

public class AdvanceUtil {
	private static Logger log = LoggerFactory.getLogger(AdvanceUtil.class);
	/**
	 * 获取网页授权凭证
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
		WeixinOauth2Token wat = null;
		String requestUrl = Constants.AUTHORIZE_ACCESSTOKEN_URL.replace("APPID", appId).
				replace("SECRET", appSecret).replace("CODE", code);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		if(null != jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				wat = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;
	}
	
	/**
	 * 刷新网页授权凭证
	 * @param appId
	 * @param refreshToken
	 * @return
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId,String refreshToken){
		WeixinOauth2Token wat = null;
		String requestUrl = Constants.AUTHORIZE_REFRESH_ACCESSTOKEN_URL.
				replace("APPID", appId).replace("REFRESH_TOKEN", refreshToken);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		if(null != jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				wat = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;
	}
	
	public static SNSUserInfo getSNSUserInfo(String accessToken,String openId){
		SNSUserInfo snsUserInfo = null;
		String requestUrl = Constants.GET_SNSUSERINFO_URL.
				replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		if(null != jsonObject){
			try{
				snsUserInfo = new SNSUserInfo();
				snsUserInfo.setOpenid(jsonObject.getString("openid"));
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				snsUserInfo.setSex(jsonObject.getIntValue("sex"));
				snsUserInfo.setCountry(jsonObject.getString("country"));
				snsUserInfo.setProvince(jsonObject.getString("province"));
				snsUserInfo.setCity(jsonObject.getString("city"));
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				snsUserInfo.setPrivilegeList(JSON.parseArray(jsonObject.getString("privilege"), String.class));
			}catch(Exception e){
				snsUserInfo = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户信息失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return null;
	}
	
	public static String urlEncodeUTF8(String source){
		String result = "";
		try {
			result = URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 上传媒体文件
	 * @param accessToken
	 * @param type 媒体文件类型（image,voice,video和thumb）
	 * @param mediaFileUrl 媒体文件的url
	 * @return
	 */
	public static WeixinMedia uploadMedia(String accessToken,String type,String mediaFileUrl){
		WeixinMedia weixinMedia = null;
		String requestUrl = Constants.GET_MEDIAID_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		String boundary = "------WebKitFormBoundaryr9KUmSWu5x9pxtCc";
		URL uploadUrl;
		try {
			uploadUrl = new URL(requestUrl);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			//设置请求头
			uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			//获取媒体文件上传的输出流
			OutputStream outputStream = uploadConn.getOutputStream();
			
			URL mediaUrl = new URL(mediaFileUrl);
			HttpURLConnection mediaConn = (HttpURLConnection) mediaUrl.openConnection();
			mediaConn.setDoOutput(true);
			mediaConn.setRequestMethod("GET");
			//从请求头中获取内容类型
			String contentType = mediaConn.getHeaderField("Content-Type");
			//根据内容类型判断文件扩展名
			String fileExt = CommonUtil.getFileExt(contentType);
			outputStream.write(("--"+boundary+"\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\";filename=\"file1%s\"\r\n",fileExt).getBytes());
			outputStream.write(String.format("Content-Type:%s\r\n\r\n", contentType).getBytes());
			
			//获取媒体文件的输入流
			BufferedInputStream bis = new BufferedInputStream(mediaConn.getInputStream());
			byte[] buf = new byte[8096];
			int size = 0;
			while((size=bis.read(buf)) != -1){
				outputStream.write(buf,0,size);
			}
			outputStream.write(("\r\n--"+boundary+"--\r\n").getBytes());
			outputStream.close();
			bis.close();
			mediaConn.disconnect();
			
			//获取媒体文件上传的输入流
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String str = null;
			while((str=bufferedReader.readLine()) != null){
				sb.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			uploadConn.disconnect();
			
			JSONObject jsonObject = JSONObject.parseObject(sb.toString());
			if(null != jsonObject){
				if(!jsonObject.containsKey("errcode")){
					weixinMedia = new WeixinMedia();
					weixinMedia.setType(jsonObject.getString("type"));
					if("thumb".equals(type)) weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
					else weixinMedia.setMediaId(jsonObject.getString("media_id"));
					weixinMedia.setCreatedAt(jsonObject.getIntValue("created_at"));
				}else{
					int errorCode= jsonObject.getIntValue("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("获取媒体id失败,errcode:{} errmsg:{}",errorCode,errorMsg);
				}
			}
		} catch (Exception e) {
			weixinMedia = null;
			log.error("获取媒体接口出错:{}",e);
		}
		return weixinMedia;
	}
	
	public static String getMedia(String accessToken,String mediaId,String savePath){
		String filePath = null;
		String requestUrl = Constants.DOWNLOAD_MEDIA_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			if(!savePath.endsWith("/")){
				savePath += "/";
			}
			String fileExt = CommonUtil.getFileExt(conn.getContentType());
			filePath = savePath + mediaId + fileExt;
			
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
			log.info("下载媒体文件成功,filePath="+filePath);
		} catch (Exception e) {
			filePath = null;
			log.error("下载媒体文件失败:{}",e);
		}
		return filePath;
	}
	
	public static void main(String[] args) throws Exception {
		String a = null;
		System.out.println(a);
		String str = "{'name':'张三','age':'10'}";
		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(str);
		BaseMessage message = new BaseMessage();
		message.setFromUserName("张三");
		message.setMsgId(123);
		
		BaseMessage message2 = new BaseMessage();
		message2.setFromUserName("李四");
		message2.setMsgId(456);
		
		List<BaseMessage> list = new ArrayList<BaseMessage>();
		list.add(message);
		list.add(message2);
		
		System.out.println(json.toJSONString());
		System.out.println(JSONObject.toJSONString(message,SerializerFeature.WriteMapNullValue));
		System.out.println(JSON.toJSONString(list));
	}
}
