package com.wechat.test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.wechat.constants.Constants;
import com.wechat.util.HttpUtil;

import net.iharder.Base64;

public class BaiduMapTest {
	@Test
	public void coordConvert() throws UnsupportedEncodingException{
		String x = "118.886131";
		String y = "32.088369";
		String from = "2";
		String mode = "";
		String requestUrl = Constants.BAIDU_MAP_CONVERT_URL.
				replace("X", x).replace("Y", y).
				replace("FROM", from).replace("MODE", mode);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		System.out.println(jsonObject.toJSONString());
		long start = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			String new_x = new String(org.apache.commons.codec.binary.Base64.decodeBase64(jsonObject.getString("x")));
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	@Test
	public void coordConvert2() throws UnsupportedEncodingException{
		String x = "118.886131";
		String y = "32.088369";
		String from = "2";
		String mode = "";
		String requestUrl = Constants.BAIDU_MAP_CONVERT_URL.
				replace("X", x).replace("Y", y).
				replace("FROM", from).replace("MODE", mode);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		System.out.println(jsonObject.toJSONString());
		long start = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			String new_x = it.sauronsoftware.base64.Base64.decode(jsonObject.getString("x"),"UTF-8");
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	@Test
	public void coordConvert3() throws Exception{
		String x = "118.886131";
		String y = "32.088369";
		String from = "2";
		String mode = "";
		String requestUrl = Constants.BAIDU_MAP_CONVERT_URL.
				replace("X", x).replace("Y", y).
				replace("FROM", from).replace("MODE", mode);
		JSONObject jsonObject = HttpUtil.doGet(requestUrl);
		System.out.println(jsonObject.toJSONString());
		long start = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			String new_x = new String(Base64.decode(jsonObject.getString("x")));
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
