package com.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.constants.Constants;
import com.wechat.menu.Button;
import com.wechat.menu.ClickButton;
import com.wechat.menu.ComplexButton;
import com.wechat.menu.Menu;
import com.wechat.menu.ViewButton;
import com.wechat.service.CoreService;
import com.wechat.util.MessageUtil;
import com.wechat.util.SignUtil;

import net.sf.json.JSONObject;

public class CoreServlet extends HttpServlet{
	private static CoreService coreService = null;
	
	public static void getInstance(){
		if(coreService == null){
			synchronized(CoreServlet.class){
				if(coreService == null){
					coreService = new CoreService();
				}
			}
		}
	}
	
	/**
	 * 容器初始化
	 */
	@Override
	public void init() throws ServletException {
		getInstance();
		coreService.initMenu();
	}
	
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
			String respXml = coreService.processRequest(req);
			PrintWriter out = resp.getWriter();
			out.write(respXml);
			out.close();
			out = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
