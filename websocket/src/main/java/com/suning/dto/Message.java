package com.suning.dto;

import java.util.List;

public class Message {
	private String id;
	private String type;
	private String content;
	private int iconNum;
	private String sign;//个性签名
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIconNum() {
		return iconNum;
	}
	public void setIconNum(int iconNum) {
		this.iconNum = iconNum;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
