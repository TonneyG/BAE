package com.gkl.vo;

public class BaseMessage {
	private String ToUserName;//������΢�ź�
	private String FromUserName;//���ͷ��˺ţ�һ��openID��
	private long CreateTime;//��Ϣ����ʱ�䣨���ͣ�
	private String MsgType;//text
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
