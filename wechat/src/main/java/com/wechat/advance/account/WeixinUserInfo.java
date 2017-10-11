package com.wechat.advance.account;

import java.util.List;

public class WeixinUserInfo {
	//关注状态（1.关注  2.未关注），未关注获取不到其余信息
	private int subscribe;
	//用户关注时间
	private String subscribeTime;
	//用户标识
	private String openid;
	//昵称
	private String nickname;
	//性别(1.男  2.女  3.未知)
	private int sex;
	//城市
	private String city;
	//省份
	private String province;
	//国家
	private String country;
	//语言
	private String language;
	//头像
	private String headImgUrl;
	//统一标识
	private String unionid;
	//公众号运营者对用户的备注
	private String remark;
	//用户所在分组
	private String groupid;
	//用户被打上标签ID的列表
	private List<String> tagidList;
	
	public int getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public List<String> getTagidList() {
		return tagidList;
	}
	public void setTagidList(List<String> tagidList) {
		this.tagidList = tagidList;
	}
}
