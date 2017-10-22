package com.wechat.ehcache;

import java.net.URL;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

public class EhcacheXml {
	/**
	 * 获取并解析缓存xml文件
	 */
	public void getConfiguration(){
		URL myUrl = this.getClass().getResource("/ehcache.xml");
		Configuration xmlConfig = new XmlConfiguration(myUrl);
		CacheManager myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
		myCacheManager.init();
		Cache<Long,String> cache = myCacheManager.getCache("simpleCache", Long.class, String.class);
		cache.put(2l, "李四");
		System.out.println(cache.get(2l));
	}
	
	public static void main(String[] args) {
		EhcacheXml ex = new EhcacheXml();
		ex.getConfiguration();
	}
}
