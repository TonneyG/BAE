package com.wechat.advance.account;

/**
 * 二维码
 * @author gkl
 *
 */
public class WeixinQRCode {
	private String ticket;
	private int expireSeconds;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpireSeconds() {
		return expireSeconds;
	}
	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}
}
