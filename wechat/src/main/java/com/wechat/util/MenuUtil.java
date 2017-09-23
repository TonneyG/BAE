package com.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.wechat.constants.Constants;
import com.wechat.menu.Button;
import com.wechat.menu.ClickButton;
import com.wechat.menu.ComplexButton;
import com.wechat.menu.Menu;
import com.wechat.menu.ViewButton;

public class MenuUtil {
	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);
	
	/**
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 凭证
	 * @return true 成功	false 失败
	 */
	public static boolean createMenu(Menu menu,String accessToken){
		boolean flag = false;
		String menuCreateUrl = Constants.CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		String jsonMenu = JSONObject.toJSONString(menu).toString();
		JSONObject jsonObject = HttpUtil.doPost(menuCreateUrl, jsonMenu);
		//net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
		if(null != jsonObject){
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if(0 == errorCode){
				flag = true;
			}else{
				flag = false;
				log.error("创建菜单失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return flag;
	}
	
	/**
	 * 查询菜单
	 * @param accessToken
	 * @return
	 */
	public static String getMenu(String accessToken){
		String result = null;
		String menuQueryUrl = Constants.QUERY_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		//JSONObject json = JSONObject.fromObject(HttpUtil.doGet(menuQueryUrl));
		JSONObject jsonObject = HttpUtil.doGet(menuQueryUrl);
		if(null != jsonObject){
			result = jsonObject.toString();
		}
		return result;
	}
	
	/**
	 * 删除菜单
	 * @param accessToken 凭证
	 * @return true 成功	false 失败
	 */
	public static boolean deleteMenu(String accessToken){
		boolean flag = false;
		String menuDeleteUrl = Constants.DELETE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpUtil.doGet(menuDeleteUrl);
		if(null != jsonObject){
			int errorCode = jsonObject.getIntValue("errcode");
			int errorMsg = jsonObject.getIntValue("errmsg");
			if(0 == errorCode){
				flag = true;
			}else{
				flag = false;
				log.error("删除菜单失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return flag;
	}
}
