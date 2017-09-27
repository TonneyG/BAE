package com.wechat.advance.account;

import java.util.List;

public class WeixinUserList {
	//关注该公众账号的总用户数
	private int total;
	//拉取的OPENID个数，最大值为10000
	private int count;
	//OPENID的列表
	private List<String> openIdList;
	//拉取列表的最后一个用户的OPENID
	private String nextOpenId;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getOpenIdList() {
		return openIdList;
	}
	public void setOpenIdList(List<String> openIdList) {
		this.openIdList = openIdList;
	}
	public String getNextOpenId() {
		return nextOpenId;
	}
	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}
}
