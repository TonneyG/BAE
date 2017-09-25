package com.wechat.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.ouath2.SNSUserInfo;
import com.wechat.ouath2.WeixinOauth2Token;
import com.wechat.util.CommonUtil;
import com.wechat.util.WebUtil;

public class OAuthServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static String APPID = "wx3a75093b38b272f7";
	private static String APPSECRET = "3b6366b1586986d06776516775a8e18b";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("gb2312");
		resp.setCharacterEncoding("gb2312");
		
		//用户同意授权后，能获取到code
		String code = req.getParameter("code");
		/*if(!"authdeny".equals(code)){
			
		}*/
		WeixinOauth2Token weixinOauth2Token = WebUtil.getOauth2AccessToken("APPID", "APPSECRET", code);
		String accessToken = weixinOauth2Token.getAccessToken();
		String openId = weixinOauth2Token.getOpenId();
		//获取用户信息
		SNSUserInfo snsUserInfo = WebUtil.getSNSUserInfo(accessToken, openId);
		
	}
}
