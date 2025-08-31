package com.supsp.springboot.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.supsp.springboot.core.consts.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
@Slf4j
public class CacherConfig {

    @Bean(CacheConstants.MANAGER_NAME)
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caffeineCaches = new ArrayList<>();
        CaffeineCache caffeineCache = new CaffeineCache(
                CoreProperties.CACHE_NAME,
                Caffeine.newBuilder()
                        //初始容量
                        .initialCapacity(CoreProperties.CACHE_INITIAL_CAPACITY)
                        //最大容量
                        .maximumSize(CoreProperties.CACHE_MAXIMUM_SIZE)
                        //过期时间
                        .expireAfterWrite(CoreProperties.CACHE_EXPIRE)
                        .build()
        );
        caffeineCaches.add(caffeineCache);
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }
}
