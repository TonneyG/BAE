package com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.wechat.constants.Constants;

import net.sf.json.JSONObject;

/**
 * @author gkl
 * 请求校验工具类
 */
public class SignUtil {
	private static String token = "gkl";
	private static String APPID = "wx3a75093b38b272f7";
	private static String APPSECRET = "3b6366b1586986d06776516775a8e18b";
	
	/**
	 * 校验签名
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 */
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] paramArr = new String[]{token,timestamp,nonce};
		Arrays.sort(paramArr);
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.getBytes());
			ciphertext = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ciphertext != null?ciphertext.equals(signature.toUpperCase()):false;
	}

	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for(int i=0;i<byteArray.length;i++){
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	
	private static String byteToHexStr(byte mByte){
		char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}
	
	public static String getAccessToken(){
		try {
			String requestUrl = Constants.ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str=reader.readLine()) != null){
				sb.append(str);
			}
			JSONObject json = JSONObject.fromObject(sb.toString());
			String accessToken = (String) json.get("access_token");
			return accessToken;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
