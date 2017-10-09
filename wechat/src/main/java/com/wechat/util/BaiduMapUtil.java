package com.wechat.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wechat.constants.Constants;
import com.wechat.extent.BaiduPlace;

public class BaiduMapUtil {
	private static String ak = "DMCG9BVjnTvgC1DyVtpz9W2hf09vHefc";
	
	public static List<BaiduPlace> searchPlace(String query,String lng,String lat){
		String requestUrl = Constants.SEARCH_POI_URL;
		Map<String,String> map = new HashMap<String,String>();
		map.put("query", "QUERY");
		
	}
}
