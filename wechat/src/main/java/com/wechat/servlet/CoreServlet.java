package com.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.constants.Constants;
import com.wechat.service.CoreService;
import com.wechat.util.MessageUtil;
import com.wechat.util.SignUtil;

public class CoreServlet extends HttpServlet{
	/**
	 * 请求校验
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//微信加密标签
		String signature = req.getParameter("signature");
		//时间戳
		String timestamp = req.getParameter("timestamp");
		//随机数
		String nonce = req.getParameter("nonce");
		//随机字符串
		String echostr = req.getParameter("echostr");
		PrintWriter out = resp.getWriter();
		if(SignUtil.checkSignature(signature,timestamp,nonce)){
			out.print(echostr);
		}
		out.close();
		out = null;
	}
	
	/**
	 * 处理微信服务器发来的消息
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		try {
			String respXml = CoreService.processRequest(req);
			PrintWriter out = resp.getWriter();
			out.write(respXml);
			out.close();
			out = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
