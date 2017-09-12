package com.wechat.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.wechat.constants.Constants;
import com.wechat.security.MyTrustManager;

public class TokenTest {
	private String APPID = "wx3a75093b38b272f7";
	private String APPSECRET = "3b6366b1586986d06776516775a8e18b";
	@Test
	public void getAccssTokenByHttpClient(){
		long start = System.currentTimeMillis();
		String requestUrl = Constants.ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(requestUrl);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity, "utf-8"));
			long end = System.currentTimeMillis();
			System.out.println(end-start);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 自定义httpClient的方式
	 */
	@Test
	public void getAccssTokenByHttpClient2() throws IOException{
		String requestUrl = Constants.ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
		HttpGet httpGet = new HttpGet(requestUrl);
		
		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		System.out.println(EntityUtils.toString(entity));
	}
	
	/**
	 * 采用绕过验证的方式处理https请求
	 */
	@Test
	public void getAccssTokenByHttpClient3() throws Exception{
		TrustManager[] tm = {new MyTrustManager()};
		SSLContext sslContext = SSLContext.getInstance("SSLv3");
		sslContext.init(null, tm, null);
		//设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslContext)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();
		String requestUrl = Constants.ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		HttpGet httpGet = new HttpGet(requestUrl);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		System.out.println(EntityUtils.toString(entity));
	}
	
	@Test
	public void getAccessTokenByConnection() throws Exception{
		try {
			long start = System.currentTimeMillis();
			String requestUrl = Constants.ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			/*TrustManager[] tm = {new MyTrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			conn.setSSLSocketFactory(ssf);*/
			//设置请求方式
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			//读取响应内容
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str=bufferedReader.readLine()) != null){
				sb.append(str);
			}
			System.out.println(sb.toString());
			long end = System.currentTimeMillis();
			System.out.println(end-start);
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
