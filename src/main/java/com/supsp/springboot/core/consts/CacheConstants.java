package com.supsp.springboot.core.consts;


import com.supsp.springboot.core.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheConstants {
    public static final String SPLIT_COLON = "::";
    public static final String MANAGER_NAME = "sysCacheManager";
    public static final String EVICT_TOPIC = "cache_evict_topic";

    public static String evictCacheTopic() {
        return CommonUtils.getPrefix() + "::CACHE::" +
                CacheConstants.EVICT_TOPIC;
    }
}
