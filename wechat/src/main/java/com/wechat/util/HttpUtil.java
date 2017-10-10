package com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class HttpUtil {
	private static String proxyHost = "222.221.152.148";
	private static int proxyPort = 80;
	
	public static String prepareParam(Map<String,Object> paramMap){
		StringBuffer sb = new StringBuffer();
		if(paramMap==null || paramMap.isEmpty()){
			return "";
		}else{
			for(String key : paramMap.keySet()){
				String value = paramMap.get(key).toString();
				if(sb.length()<1){
					sb.append(key).append("=").append(value);
				}else{
					sb.append("&").append(key).append("=").append(value);
				}
			}
			return sb.toString();
		}
	}
	
	public static JSONObject doGet(String requestUrl){
		return doGet(requestUrl,null);
	}
	
	public static String doGet_Original(String requestUrl,Map<String,Object> paramMap){
		try {
			//拼接url参数
			String paramStr = prepareParam(paramMap);
			if(paramStr != null && paramStr.trim().length()>0){
				requestUrl += "?"+paramStr;
			}
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader br = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str = br.readLine())!=null){
				sb.append(str);
			}
			//关闭、释放资源
			br.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject doGet(String requestUrl,Map<String,Object> paramMap){
		try {
			//拼接url参数
			String paramStr = prepareParam(paramMap);
			if(paramStr != null && paramStr.trim().length()>0){
				requestUrl += "?"+paramStr;
			}
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader br = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str = br.readLine())!=null){
				sb.append(str);
			}
			//关闭、释放资源
			br.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
			return stringToJson(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject doPost(String requestUrl,Map<String,Object> paramMap){
		try {
			String paramStr = prepareParam(paramMap);
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//追加post请求参数
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(paramStr.getBytes("utf-8"));
			os.close();
			
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader br = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str = br.readLine())!=null){
				sb.append(str);
			}
			//关闭、释放资源
			br.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
			return stringToJson(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject doPost(String requestUrl,String paramStr){
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//追加post请求参数
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(paramStr.getBytes("utf-8"));
			os.close();
			
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader br = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str = br.readLine())!=null){
				sb.append(str);
			}
			//关闭、释放资源
			br.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
			return stringToJson(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject doGetWithProxy(String requestUrl,Map<String,Object> paramMap){
		try {
			//拼接url参数
			String paramStr = prepareParam(paramMap);
			if(paramStr != null && paramStr.trim().length()>0){
				requestUrl += "?"+paramStr;
			}
			URL url = new URL(requestUrl);
			//创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(proxyHost,proxyPort);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP,addr));
			String headerKey = "Proxy-Authorization";
			String headerValue = "Basic "+Base64.encodeBase64String("atco:atco".getBytes());
			conn.setRequestProperty(headerKey, headerValue);
			
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader br = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str = br.readLine())!=null){
				sb.append(str);
			}
			//关闭、释放资源
			br.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
			return stringToJson(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject doPostWithProxy(String requestUrl,Map<String,Object> paramMap){
		try {
			//拼接url参数
			String paramStr = prepareParam(paramMap);
			URL url = new URL(requestUrl);
			//创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(proxyHost,proxyPort);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP,addr));
			String headerKey = "Proxy-Authorization";
			String headerValue = "Basic "+Base64.encodeBase64String("atco:atco".getBytes());
			conn.setRequestProperty(headerKey, headerValue);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(paramStr.getBytes("utf-8"));
			os.close();
			
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader br = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str = br.readLine())!=null){
				sb.append(str);
			}
			//关闭、释放资源
			br.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
			return stringToJson(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//特别的，如果使用json-lib如果使用getXXX方法获取的值为null，会抛出JSONException异常，而fastjson不会，所以对应判断逻辑需要改变
	public static JSONObject stringToJson(String content){
		JSONObject jsonObject = JSONObject.parseObject(content);
		return jsonObject;
	}
	
}
