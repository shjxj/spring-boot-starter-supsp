package com.supsp.springboot.core.config;


import com.supsp.springboot.core.consts.CacheConstants;
import com.supsp.springboot.core.utils.CacheUtils;
import com.supsp.springboot.core.utils.StrUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.Cache;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacherEvictListener implements ApplicationRunner, Ordered {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RTopic topic = redissonClient.getTopic(
                CacheConstants.evictCacheTopic()
        );
        topic.addListener(String.class, (channel, msg) -> {
            if (StrUtils.isBlank(msg)) {
                return;
            }
            String[] split = msg.split(CacheConstants.SPLIT_COLON);
            Cache cache = null;
            String cacheKey = "";
            if (split.length < 2) {
                cache = CacheUtils.getCache();
                cacheKey = msg;
            } else {
                cache = CacheUtils.getCache(split[0]);
                cacheKey = StrUtils.join(
                        ArrayUtils.remove(split, 0),
                        CacheConstants.SPLIT_COLON
                );
            }
            if (ObjectUtils.isEmpty(cache)) {
                return;
            }
            cacheEvict(cache, cacheKey);
        });
    }

    private void cacheEvict(Cache cache, String cacheKey) {
        if (ObjectUtils.isEmpty(cache)) {
            return;
        }
        try {
            CacheUtils.evict(cache, cacheKey);
        } catch (Exception e) {
            log.error("error message", e);
        }
    }

    @Override
    public int getOrder() {
        return 9;
    }
}
