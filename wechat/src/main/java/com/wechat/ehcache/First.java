package com.wechat.ehcache;

import java.net.URL;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.junit.Test;

public class First {
	@Test
	public void demo(){
		//构建一个别名为"preConfigured"的Cache对象，并设置其键值对类型和缓存个数
		//不带参数的build()方法返回一个完全实例化但未初始化的CacheManager，可以调用build(true)或cacheManager.init()方法初始化
		/*CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().
				withCache("preConfigured", 
						CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100))
						.build())
				.build(true);*/
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().
		withCache("preConfigured", 
				CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100))
				.build())
		.build();
		cacheManager.init();
		//获取Cache对象
		Cache<Long,String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);
		//可以通过cacheManager.createCache()创建一个新的Cache
		Cache<Long,String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)));
		myCache.put(1L, "first");
		String value = myCache.get(1L);
		//删除并关闭某个具体的cache
		cacheManager.removeCache("preConfigured");
		//关闭释放所有的cache资源
		cacheManager.close();
	}
}
