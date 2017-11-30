package com.suning.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * 在多用户多设备登录的情况下
 * @author 17080127
 */
//@ServerEndpoint(value="/WebSocketWithMoreDevice/${userId}")
public class WebSocketWithMoreDevice {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	//记录每个用户下多个终端的连接
	private static Map<Long,Set<WebSocketWithMoreDevice>> userSocket = new HashMap<Long,Set<WebSocketWithMoreDevice>>();
	//需要session来对用户发送数据, 获取连接特征userId
	private Session session;
	private Long userId;

	@OnOpen
	public void onOpen(Long userId,Session session){
		this.userId = userId;
		this.session = session;
		onlineCount++;
		if(userSocket.containsKey(this.userId)){
			userSocket.get(userId).add(this);
		}else{
			Set<WebSocketWithMoreDevice> set = new HashSet<WebSocketWithMoreDevice>();
			set.add(this);
			userSocket.put(this.userId, set);
		}
	}
	
	/**
     * @Title: onMessage
     * @Description: 收到消息后的操作
     * @param @param message 收到的消息
     * @param @param session 该连接的session属性
     */
    @OnMessage
    public void onMessage(String message, Session session) {    
    }

    /**
     * @Title: onError
     * @Description: 连接发生错误时候的操作
     * @param @param session 该连接的session
     * @param @param error 发生的错误
     */
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }

    public Boolean sendMessageToUser(Long userId,String message){
        if (userSocket.containsKey(userId)) {
            for (WebSocketWithMoreDevice WS : userSocket.get(userId)) {
                try {
                    WS.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
