package com.suning.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suning.dto.Message;

@ServerEndpoint("/fingerGuess")
public class GameSocket {
	private Session session;
	private static Map<String,GameSocket> gameSocketsMap = new HashMap<String,GameSocket>();
	//所有在线玩家
	private static List<Message> playerList = new ArrayList<Message>();
	//当前玩家
	private static Message currentPlayer = new Message(); 
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
	}
	
	@OnMessage
	public void onMessage(String message,Session session) throws Exception{
		JSONObject jsonObject = JSON.parseObject(message);
		String type = jsonObject.getString("type");
		String content = jsonObject.getString("data");
		if(type.equals("connect")){
			Message msg = constructMsg(content,type);
			//通知当前客户端所有在线用户
			playerList.add(0,msg);
			session.getBasicRemote().sendText(JSON.toJSONString(playerList));
			//通知非当前客户端的用户有新用户上线了
			for(GameSocket client : gameSocketsMap.values()){
				List<Message> list = new ArrayList<Message>();
				list.add(msg);
				currentPlayer = msg;
				client.session.getBasicRemote().sendText(JSON.toJSONString(list));
			}
			gameSocketsMap.put(session.getId(), this);
		}else if(type.equals("text")){
			
		}else if(type.equals("publicChat")){
			Message msg = constructMsg(content,type);
			for(GameSocket client : gameSocketsMap.values()){
				client.session.getBasicRemote().sendText(JSON.toJSONString(msg));
			}
		}
	}
	
	@OnClose
	public void onClose(Session session) throws IOException{
		GameSocket gs = gameSocketsMap.get(session.getId());
		Message msg = gs.currentPlayer;
		gameSocketsMap.remove(session.getId());
		for(GameSocket client : gameSocketsMap.values()){
			msg.setType("disconnect");
			client.session.getBasicRemote().sendText(JSON.toJSONString(msg));
		}
	}
	
	@OnError
	public void onError(Session session,Throwable error) throws IOException{
		session.close();
		playerList.clear();
		gameSocketsMap.remove(session.getId());
		System.out.println("发生错误");
		error.printStackTrace();
	}
	
	private Message constructMsg(String content,String type){
		Message msg = new Message();
		msg.setId(session.getId());
		msg.setContent(content);
		msg.setIconNum((int)(Math.floor((Math.random()*11)+1)));
		msg.setType(type);
		return msg;
	}
}
