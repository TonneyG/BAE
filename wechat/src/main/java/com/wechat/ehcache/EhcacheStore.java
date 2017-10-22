package com.wechat.ehcache;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

public class EhcacheStore {
	public static void persistCache(){
		PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
			    .with(CacheManagerBuilder.persistence(new File("C:/Ehcache/cache", "myData"))) 
			    .withCache("threeTieredCache",
			        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
			            ResourcePoolsBuilder.newResourcePoolsBuilder()
			                .heap(10, EntryUnit.ENTRIES) 
			                .offheap(1, MemoryUnit.MB) 
			                .disk(20, MemoryUnit.MB, true) 
			            ).withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
			    ).build(true);

			Cache<Long, String> threeTieredCache = persistentCacheManager.getCache("threeTieredCache", Long.class, String.class);
			threeTieredCache.put(1L, "stillAvailableAfterRestart"); 
			persistentCacheManager.close();
	}
	
	public static void main(String[] args) {
		//persistCache();
		PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
			    .with(CacheManagerBuilder.persistence(new File("C:/Ehcache/cache", "myData"))) 
			    .withCache("threeTieredCache",
			        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
			            ResourcePoolsBuilder.newResourcePoolsBuilder()
			                .heap(10, EntryUnit.ENTRIES) 
			                .offheap(1, MemoryUnit.MB) 
			                .disk(20, MemoryUnit.MB, true) 
			            ).withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS)))
			    ).build(true);
		Cache<Long, String> threeTieredCache = persistentCacheManager.getCache("threeTieredCache", Long.class, String.class);
		System.out.println(threeTieredCache.get(1L));
		persistentCacheManager.close();
	}
}
