package com.wechat.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechat.constants.Constants;
import com.wechat.extent.BaiduPlace;
import com.wechat.extent.UserLocation;
import com.wechat.message.response.Article;

public class BaiduMapUtil {
	private static String ak = "DMCG9BVjnTvgC1DyVtpz9W2hf09vHefc";
	
	public static List<BaiduPlace> searchPlace(String query,String lng,String lat){
		String requestUrl = Constants.SEARCH_POI_URL;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("query", query);
		map.put("location", lat+","+lng);
		map.put("scope", 2);
		map.put("radius", 2000);
		map.put("output", "xml");
		map.put("ak", ak);
		String xml = HttpUtil.doGet_Original(requestUrl, map);
		List<BaiduPlace> placeList = parsePlaceXml(xml);
		return placeList;
	}
	
	private static List<BaiduPlace> parsePlaceXml(String xml){
		List<BaiduPlace> placeList = null;
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			Element element = root.element("results");
			List<Element> resultElementList = element.elements();
			if(resultElementList.size()>0){
				placeList = new ArrayList<BaiduPlace>();
				//POI名称
				Element nameElement = null;
				//POI地址
				Element addressElement = null;
				//POI经纬度坐标
				Element locationElement = null;
				//POI电话信息
				Element telephoneElement = null;
				//POI扩展信息
				Element detailElement = null;
				//距离中心点的距离
				Element distanceElement = null;
				for(Element ele : resultElementList){
					nameElement = ele.element("name");
					addressElement = ele.element("address");
					locationElement = ele.element("location");
					telephoneElement = ele.element("telephone");
					detailElement = ele.element("detail_info");
					
					BaiduPlace place = new BaiduPlace();
					place.setName(nameElement.getText());
					place.setAddress(addressElement.getText());
					place.setLng(locationElement.element("lng").getText());
					place.setLat(locationElement.elementText("lat"));
					if(null != telephoneElement) place.setTelephone(telephoneElement.getText());
					if(null != detailElement){
						distanceElement = detailElement.element("distance");
						if(null != distanceElement){
							place.setDistance(Integer.parseInt(distanceElement.getText()));
						}
					}
					placeList.add(place);
				}
			}
			//距离由近及远排序
			Collections.sort(placeList);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return placeList;
	}
	
	public static List<Article> makeArticleList(String basePath,List<BaiduPlace> placeList,String userLng,String userLat){
		List<Article> list = new ArrayList<Article>();
		BaiduPlace place = null;
		for(int i=0;i<placeList.size();i++){
			place = placeList.get(i);
			Article article = new Article();
			article.setTitle(place.getName()+"\n距离约"+place.getDistance()+"米");
			//p1表示用户发送的位置(坐标转换后),p2表示当前POI所在位置
			article.setUrl(String.format(basePath+"route.jsp?p1=%s,%s&p2=%s,%s",userLng,userLat,place.getLng(),place.getLat()));
			//设置首条图文消息的图片
			if(i == 0){
				article.setPicUrl(basePath+"img/bigNews.jpg");
			}else{
				article.setPicUrl(basePath+"img/smallNews.jpg");
			}
			list.add(article);
		}
		return list;
	}
	
	public static UserLocation convertCoord(String lng,String lat){
		String convertUrl = Constants.BAIDU_MAP_CONVERT2_URL.replace("${COORDS}", lng+","+lat).
				replace("${FROM}", "1").replace("${to}", "5").replace("${ak}",ak);
		JSONObject jsonObject = HttpUtil.doGet(convertUrl);
		UserLocation userLocation = null;
		if(null != jsonObject){
			if(!jsonObject.containsKey("errcode")){
				userLocation = new UserLocation();
				userLocation.setBd09Lng(((JSONObject)jsonObject.getJSONArray("result").get(0)).getString("x"));
				userLocation.setBd09Lat(((JSONObject)jsonObject.getJSONArray("result").get(0)).getString("y"));
			}
		}
		return userLocation;
	}
	
	public static void main(String[] args) {
		List<BaiduPlace> placeList = searchPlace("酒店","116.404","39.915");
		System.out.println(placeList);
		
		String json = "{\"status\":0,\"result\":[{\"x\":114.23074978833,\"y\":29.579088056668}]}";
		JSONObject jsonObject = JSONObject.parseObject(json);
		JSONArray array = jsonObject.getJSONArray("result");
		System.out.println(((JSONObject)array.get(0)).get("x"));
	}
}
