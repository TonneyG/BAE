package com.gkl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gkl.util.CheckUtil;
import com.gkl.util.MessageUtil;
import com.gkl.vo.TextMessage;

public class WeixinServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");

		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		out.close();
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp){
		PrintWriter out = null;
		resp.setCharacterEncoding("UTF-8");
		try {
			out = resp.getWriter();
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					//message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("2".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
				}else if("3".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.thirdMenu());
				}else if("?".equals(content) || "£¿".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				System.out.println(message);
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}
			out.print(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.close();
		}
	}
}
