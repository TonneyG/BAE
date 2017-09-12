package com.wechat.util;

import com.wechat.constants.Constants;
import com.wechat.menu.Button;
import com.wechat.menu.ClickButton;
import com.wechat.menu.ComplexButton;
import com.wechat.menu.Menu;
import com.wechat.menu.ViewButton;

import net.sf.json.JSONObject;

public class MenuUtil {
	public static void createMenu(){
		String accessToken = SignUtil.getAccessToken();
		String menuCreateUrl = Constants.CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		Menu menu = initMenu();
		String jsonMenu = JSONObject.fromObject(menu).toString();
	}
	
	public static Menu initMenu(){
		ClickButton btn1 = new ClickButton();
		btn1.setName("天气");
		btn1.setKey("b_weather");
		
		ViewButton btn2 = new ViewButton();
		
		ClickButton btn31 = new ClickButton();
		btn31.setName("赞一个");
		btn31.setKey("b_praise");
		
		ClickButton btn32 = new ClickButton();
		btn32.setName("热歌速递");
		btn32.setKey("b_hot_top");
		
		ClickButton btn33 = new ClickButton();
		btn33.setName("新歌驾到");
		btn33.setKey("b_new_top");
		
		ComplexButton btn3 = new ComplexButton();
		btn3.setName("音乐潮流榜");
		btn3.setSub_button(new Button[]{btn31,btn32,btn33});
		Menu menu = new Menu();
		menu.setButton(new Button[]{btn1,btn3});
		return menu;
	}
}
