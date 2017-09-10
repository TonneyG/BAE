package com.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.constants.Constants;
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Map<String,String> map = MessageUtil.parseXml(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String msgId = map.get("MsgId");
			//文本消息
			if(msgType.equals(Constants.REQ_TEXT_MESSAGE)){
				String content = map.get("Content");
				//TODO 处理文本消息请求
			}else if(msgType.equals(Constants.REQ_IMAGE_MESSAGE)){
				String picUrl = map.get("PicUrl");
				//TODO 处理图片消息请求
			}else if(msgType.equals(Constants.REQ_VOICE_MESSAGE)){
				String mediaId = map.get("MediaId");
				String format = map.get("Format");
				//TODO 处理语音消息请求
			}else if(msgType.equals(Constants.REQ_VIDEO_MESSAGE)){
				String mediaId = map.get("MediaId");
				String thumbMediaId = map.get("ThumbMediaId");
				//TODO 处理视频消息请求
			}else if(msgType.equals(Constants.REQ_LOCATION_MESSAGE)){
				String lat = map.get("Location_X");
				String lon = map.get("Location_Y");
				String label = map.get("Label");
				String scale = map.get("Scale");
				//TODO 处理地理位置消息请求
			}else if(msgType.equals(Constants.REQ_LINK_MESSAGE)){
				String title = map.get("Title");
				String description = map.get("Description");
				String url = map.get("Url");
				//TODO 处理链接消息请求
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
