package com.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.constants.Constants;
import com.wechat.menu.Button;
import com.wechat.menu.ClickButton;
import com.wechat.menu.ComplexButton;
import com.wechat.menu.Menu;
import com.wechat.menu.ViewButton;

import net.sf.json.JSONObject;

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
		String jsonMenu = JSONObject.fromObject(menu).toString();
		String result = HttpUtil.doPost(menuCreateUrl, jsonMenu);
		JSONObject json = JSONObject.fromObject(result);
		if(null != json){
			int errorCode = json.getInt("errcode");
			String errorMsg = json.getString("errmsg");
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
		JSONObject json = JSONObject.fromObject(HttpUtil.doGet(menuQueryUrl));
		if(null != json){
			result = json.toString();
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
		JSONObject json = JSONObject.fromObject(HttpUtil.doGet(menuDeleteUrl));
		if(null != json){
			int errorCode = json.getInt("errcode");
			int errorMsg = json.getInt("errmsg");
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
