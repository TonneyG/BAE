package com.wechat.security;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class MyTrustManager implements X509TrustManager {

	//检查客户端证书
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	}

	//检查服务器端证书
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	}

	//返回受信任的X509证书数组
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
