package com.gkl.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gkl.vo.AccessToken;

public class WeiXinUtil {
	private static String APPID = "wx3a75093b38b272f7";
	private static String APPSECRET = "3b6366b1586986d06776516775a8e18b";
	private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	public static void main(String[] args) {
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		doGetStr(url);
	}
	
	public static String doGetStr(String url){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response;
		String result = "";
		try {
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				result = EntityUtils.toString(entity,"UTF-8");
				Object jsonObject = JSON.toJSON(entity);
				JSON.toJSONString(entity);
				JSONObject.toJSON(entity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result; 
	}
	
	public static String doPostStr(String url,String outStr){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
		String result = "";
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				result = EntityUtils.toString(entity,"UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		String result = doGetStr(url);
		System.out.println(result);
		if(result != null){
			token.setToken("");
			token.setExpiresIn("");
		}
		return token;
	}
	
	public static String upload(String filePath,String accessToken,String type) throws IOException{
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()){
			throw new IOException("文件不存在");
		}
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		//连接及设置属性
		URL urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset","UTF-8");
		//设置边界
		String BOUNDARY = "--------"+System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="+BOUNDARY);
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition:form-data;name=\"file\";filename=\""+file.getName()+"\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		
		byte[] head = sb.toString().getBytes("UTF-8");
		//获得输出流并输出表头
		OutputStream out = new DataOutputStream(con.getOutputStream());
		out.write(head);
		//把文件以流的方式输出到url
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while((bytes=in.read(bufferOut)) != -1){
			out.write(bufferOut,0,bytes);
		}
		in.close();
		
		//结尾
		byte[] foot = ("\r\n--"+BOUNDARY+"--\r\n").getBytes("UTF-8");//定义最后的数据分割线
		out.write(foot);
		out.flush();
		out.close();
		
		StringBuffer buffer = new StringBuffer();
		String result = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		while((line = reader.readLine()) != null){
			buffer.append(line);
		}
		if(result == null){
			result = buffer.toString();
		}
		reader.close();
		
		/*JSONObject json = JSONObject.fromObject(result);
		String mediaId = json.getString("media_id");
		return mediaId;*/
		return "";
	}
}
