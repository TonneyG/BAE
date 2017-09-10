package com.wechat.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.junit.Test;

import com.wechat.constants.Constants;

public class TokenTest {
	private String APPID = "wx3a75093b38b272f7";
	private String APPSECRET = "3b6366b1586986d06776516775a8e18b";
	@Test
	public void getAccssTokenByHttpClient(){
		
	}

	@Test
	public void getAccessTokenByConnection(){
		try {
			String requestUrl = Constants.ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			//设置请求方式
			conn.setRequestMethod("GET");
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			//读取响应内容
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str=bufferedReader.readLine()) != null){
				sb.append(str);
			}
			System.out.println(sb.toString());
			//关闭释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
