package com.supsp.springboot.core.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.CacheConstants;
import com.supsp.springboot.core.helper.SystemCommon;
import com.supsp.springboot.core.threads.CacheTables;
import com.supsp.springboot.core.vo.CacheData;
import com.supsp.springboot.core.vo.CacheTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CacheUtils {

    /**
     * 获取 CacheManager
     *
     * @param cacheBeanName
     * @return
     */
    public static CacheManager getCacheManager(String cacheBeanName) {
        try {
            return SpringUtil.getBean(cacheBeanName, CacheManager.class);
        } catch (Exception e) {
            // log.error("CacheManager error", e);
        }
        return null;
    }

    /**
     * 获取 CacheManager
     *
     * @return
     */
    public static CacheManager getCacheManager() {
        return getCacheManager(CacheConstants.MANAGER_NAME);
    }

    /**
     * 获取 Cache
     *
     * @param cacheName
     * @param cacheBeanName
     * @return
     */
    public static Cache getCache(String cacheName, String cacheBeanName) {
        CacheManager cacheManager = getCacheManager(cacheBeanName);
        if (ObjectUtils.isEmpty(cacheManager)) {
            return null;
        }
        try {
            return cacheManager.getCache(cacheName);
        } catch (Exception e) {
            log.error("error message", e);
        }
        return null;
    }

    /**
     * 获取 Cache
     *
     * @param cacheName
     * @return
     */
    public static Cache getCache(String cacheName) {
        return getCache(cacheName, CacheConstants.MANAGER_NAME);
    }

    /**
     * 获取 Cache
     *
     * @return
     */
    public static Cache getCache() {
        return getCache(CoreProperties.CACHE_NAME);
    }

    /**
     * 获取 Cache 值
     *
     * @param cache
     * @param key
     * @return
     */
    public static Cache.ValueWrapper valueWrapper(Cache cache, Object key) {
        if (
                ObjectUtils.isEmpty(cache)
                        || ObjectUtils.isEmpty(key)
        ) {
            return null;
        }
        try {
            return cache.get(key);
        } catch (Exception e) {
            log.error("error message", e);
        }
        return null;
    }

    /**
     * 获取 Cache 值
     *
     * @param key
     * @return
     */
    public static Cache.ValueWrapper valueWrapper(Object key) {
        if (ObjectUtils.isEmpty(key)) {
            return null;
        }
        return valueWrapper(getCache(), key);
    }

    /**
     * 获取 Cache 值
     *
     * @param cache
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T value(Cache cache, Object key) {
        if (ObjectUtils.isEmpty(cache) || ObjectUtils.isEmpty(key)) {
            return null;
        }
        Cache.ValueWrapper cachedValueWrapper = valueWrapper(cache, key);
        if (ObjectUtils.isEmpty(cachedValueWrapper)) {
            return null;
        }
        try {
            @SuppressWarnings("unchecked")
            T val = (T) cachedValueWrapper.get();
            return val;
        } catch (Exception e) {
            log.error("Error exception: ", e);
        }
        return null;
    }

    /**
     * 获取 cache
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T value(Object key) {
        return value(getCache(), key);
    }

    /**
     * 清理cache
     *
     * @param cache
     * @param key
     */
    public static void evict(Cache cache, Object key) {
        if (ObjectUtils.isEmpty(cache) || ObjectUtils.isEmpty(key)) {
            return;
        }
        try {
            cache.evict(key);
        } catch (Exception e) {
            log.error("error message", e);
        }
    }

    /**
     * 清理cache
     *
     * @param key
     */
    public static void evict(Object key) {
        evict(getCache(), key);
    }

    /**
     * 写入 cache
     *
     * @param cache
     * @param key
     * @param value
     */
    public static void put(Cache cache, Object key, Object value) {
        if (ObjectUtils.isEmpty(cache) || ObjectUtils.isEmpty(key)) {
            return;
        }
        try {
            cache.put(key, value);
        } catch (Exception e) {
            log.error("error message", e);
        }
    }

    /**
     * 设置 cache
     *
     * @param key
     * @param value
     */
    public static void put(Object key, Object value) {
        put(getCache(), key, value);
    }

    public static final String CACHE_TABLE_KEY_PREFIX = "CACHE_TABLE::";

    /**
     * 获取缓存表名称
     *
     * @param tableNameKey
     * @return
     */
    public static String cacheTableKey(String tableNameKey) {
        return CACHE_TABLE_KEY_PREFIX + tableNameKey;
    }

    public static String cacheTableNameKey(String table) {
        if (StrUtils.isBlank(table)) {
            return "";
        }
        return table.trim();
    }


    /**
     * 获取缓存表名称
     *
     * @param table
     * @return
     */
    public static String cacheTableNameKey(CacheTable table) {
        if (table == null) {
            return "";
        }
        return cacheTableNameKey(table.getTable());
    }

    /**
     * 获取缓存表名称
     *
     * @param table
     * @return
     */
    public static String cacheTableKey(CacheTable table) {
        if (table == null) {
            return null;
        }
        return cacheTableKey(cacheTableNameKey(table));
    }

    /**
     * 设置缓存表
     *
     * @param table
     */
    public static void setCacheTable(CacheTable table) {
        if (table == null) {
            return;
        }
        String key = cacheTableKey(table);
        if (StrUtils.isBlank(key)) {
            return;
        }
        RedisUtils.set(
                key,
                table,
                CoreProperties.CACHE_TABLE_EXPIRE
        );
    }

    /**
     * 设置缓存表
     *
     * @param cacheTable
     */
    public static void setCacheTable(String cacheTable) {
        if (StrUtils.isBlank(cacheTable)) {
            return;
        }
        setCacheTable(
                CacheTable.cacheTable(cacheTable)
        );
    }

    /**
     * 获取表缓存数据有效性
     *
     * @param cacheTable
     * @return
     */
    public static CacheTable validateCacheTable(CacheTable cacheTable) {
        if (
                ObjectUtils.isEmpty(cacheTable)
                        || StrUtils.isEmpty(cacheTable.getTable())
                        || cacheTable.getExpiresAt() < CommonUtils.timestamp()
                        || !cacheTable.getVersion().equals(CoreProperties.CACHE_VERSION)
        ) {
            return null;
        }
        return cacheTable;
    }

    /**
     * 获取表缓存
     *
     * @param table
     * @return
     */
    public static CacheTable getCacheTable(CacheTable table) {
        if (table == null) {
            return null;
        }
        String key = cacheTableKey(table);
        if (StrUtils.isBlank(key)) {
            return null;
        }
        CacheTable cacheTable = RedisUtils.get(
                key
        );
        if (ObjectUtils.isEmpty(cacheTable)) {
            return null;
        }
        return validateCacheTable(cacheTable);
    }

    public static CacheTable getCacheTable(String tableName) {
        String key = cacheTableKey(cacheTableNameKey(tableName));
        if (StrUtils.isBlank(key)) {
            return null;
        }
        CacheTable cacheTable = RedisUtils.get(
                key
        );
        if (ObjectUtils.isEmpty(cacheTable)) {
            return null;
        }
        return validateCacheTable(cacheTable);
    }

    public static Map<String, CacheTable> cacheTablesMap(List<String> tables) {
        if (CommonUtils.isEmpty(tables)) {
            return null;
        }
        List<Serializable> tableKeys = tables.stream()
                .filter(StrUtils::isNotBlank)
                .map(CacheUtils::cacheTableKey)
                .collect(Collectors.toList());
        if (CommonUtils.isEmpty(tableKeys)) {
            return null;
        }
        List<CacheTable> cacheTables = RedisUtils.multiGet(tableKeys);
        if (CommonUtils.isEmpty(cacheTables)) {
            return null;
        }
        Map<String, CacheTable> cacheTableMap = new HashMap<>();
        int i = 0;
        try {
            assert cacheTables != null;
            for (String table : tables) {
                if (StrUtils.isBlank(table)) {
                    continue;
                }
                cacheTableMap.put(
                        table,
                        cacheTables.get(i)
                );
                i++;
            }
        } catch (Exception e) {
            //
        }
        return cacheTableMap;
    }

    /**
     * 获取相关表缓存
     *
     * @param tables
     * @return
     */
    public static Map<String, CacheTable> getCacheTableMap(List<String> tables) {
        if (CommonUtils.isEmpty(tables)) {
            return null;
        }
        Map<String, CacheTable> cacheTableMap = cacheTablesMap(tables);
        if (CommonUtils.isEmpty(cacheTableMap)) {
            return null;
        }
        assert cacheTableMap != null;
        return cacheTableMap.entrySet().stream()
                .filter(v -> ObjectUtils.isNotEmpty(validateCacheTable(v.getValue())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> newVal
                ));
    }

    /**
     * 获取相关表
     *
     * @return
     */
    public static List<String> cacheTables() {
        Map<String, CacheTable> tableMap = CacheTables.getLocalMap();
        if (CommonUtils.isEmpty(tableMap)) {
            return null;
        }
        return tableMap.values().stream()
                .map(CacheUtils::cacheTableNameKey)
                .toList();
    }

    /**
     * 获取缓存信息数据
     *
     * @param data
     * @param timeout
     * @param timeUnit
     * @param <T>
     * @return
     */
    public static <T> CacheData<T> cacheData(T data, long timeout, TimeUnit timeUnit) {
        long timestamp = CommonUtils.timestamp();
        long expiresAt = timestamp + TimeUnit.SECONDS.convert(timeout, timeUnit);
        return CacheData.<T>builder()
                .data(data)
                .module(SystemCommon.getModuleName())
                .app(SystemCommon.getAppName())
                .timestamp(timestamp)
                .expiresAt(expiresAt)
                .version(CoreProperties.CACHE_VERSION)
                .tables(cacheTables())
                .build();
    }

    /**
     * 校验缓存与表缓存有效性
     *
     * @param timestamp
     * @param tables
     * @return
     */
    public static boolean validateTables(long timestamp, List<String> tables) {
        if (CommonUtils.isEmpty(tables)) {
            return true;
        }
        Map<String, CacheTable> cacheTableMap = getCacheTableMap(tables);
        if (CommonUtils.isEmpty(cacheTableMap)) {
            return false;
        }
        assert cacheTableMap != null;
        for (String table : tables) {
            if (StrUtils.isBlank(table)) {
                continue;
            }
            CacheTable cacheTable = cacheTableMap.getOrDefault(table, null);
            if (cacheTable == null) {
                return false;
            }
            if (cacheTable.getTimestamp() >= timestamp) {
                return false;
            }
        }
        return true;
    }

    public static void initCacheTables(List<String> tables) {
        if (CommonUtils.isEmpty(tables)) {
            return;
        }
        Map<String, CacheTable> cacheTableMap = cacheTablesMap(tables);
        for (String table : tables) {
            if (StrUtils.isBlank(table)) {
                continue;
            }
            CacheTable cacheTable = cacheTableMap != null ? cacheTableMap.getOrDefault(table, null) : null;
            if (cacheTable == null) {
                setCacheTable(table);
            }
        }
    }

    /**
     * 校验缓存有效性
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> T validateCache(CacheData<T> data) {
        if (ObjectUtils.isEmpty(data)) {
            return null;
        }
        T value = null;
        try {
            value = data.getData();
        } catch (Exception e) {
            return null;
        }
        if (
                ObjectUtils.isEmpty(value)
                        || !data.getVersion().equals(CoreProperties.CACHE_VERSION)
                        || data.getExpiresAt() < CommonUtils.timestamp()
        ) {
            return null;
        }
        if (!validateTables(data.getTimestamp(), data.getTables())) {
            return null;
        }
        return value;
    }

    /**
     * redis 缓存 key
     *
     * @param cacheName
     * @param cacheKey
     * @return
     */
    public static String redisCacheKey(String cacheName, Object cacheKey) {
        if (ObjectUtils.isEmpty(cacheKey)) {
            return null;
        }
        if (StrUtils.isEmpty(cacheName)) {
            cacheName = CoreProperties.CACHE_NAME;
        }
        return cacheName + CacheConstants.SPLIT_COLON + cacheKey;
    }

    /**
     * 写入 redis 缓存
     *
     * @param cacheName
     * @param cacheKey
     * @param cacheValue
     * @param expire
     * @param timeUnit
     */
    public static <T> void putRedisCache(
            String cacheName,
            Object cacheKey,
            T cacheValue,
            long expire,
            TimeUnit timeUnit
    ) {
        String key = redisCacheKey(cacheName, cacheKey);
        if (StrUtils.isBlank(key)) {
            return;
        }
        if (ObjectUtils.isEmpty(cacheValue)) {
            RedisUtils.delete(key);
            return;
        }
        CacheData<T> cacheData = cacheData(
                cacheValue,
                expire,
                timeUnit
        );
//        if (!CommonTools.isEnvProduct()) {
//            log.debug(
//                    "■■■■■■ CACHE PUT [REDIS] - {} ■■■■■■\n{}\n{}",
//                    cacheName,
//                    cacheKey,
//                    JsonUtil.toJSONString(cacheData.getTables())
//            );
//        }
        RedisUtils.set(
                key,
                cacheData,
                expire,
                timeUnit
        );
        if (CommonUtils.isEmpty(cacheData.getTables())) {
            return;
        }
        initCacheTables(cacheData.getTables());
    }

    /**
     * 获取 redis 缓存
     *
     * @param cacheName
     * @param cacheKey
     * @param <T>
     * @return
     */
    public static <T> T getRedisCache(String cacheName, Object cacheKey) {
        String key = redisCacheKey(cacheName, cacheKey);
        if (StrUtils.isBlank(key)) {
            return null;
        }
        CacheData<T> data = RedisUtils.get(key);
        if (ObjectUtils.isEmpty(data)) {
            return null;
        }
        return validateCache(data);
    }

    public static void deleteRedisCache(String cacheName, Object cacheKey) {
        String key = redisCacheKey(cacheName, cacheKey);
        if (StrUtils.isBlank(key)) {
            return;
        }
        RedisUtils.delete(key);
    }

    /**
     * 写入本地缓存
     *
     * @param cacheName
     * @param cacheKey
     * @param cacheValue
     * @param expire
     * @param timeUnit
     */
    public static <T> void putLocalCache(
            String cacheName,
            Object cacheKey,
            T cacheValue,
            long expire,
            TimeUnit timeUnit
    ) {
        if (ObjectUtils.isEmpty(cacheKey)) {
            return;
        }
        if (StrUtils.isEmpty(cacheName)) {
            cacheName = CoreProperties.CACHE_NAME;
        }
        Cache cache = getCache(cacheName);
        if (ObjectUtils.isEmpty(cache)) {
            return;
        }
        if (ObjectUtils.isEmpty(cacheValue)) {
            evict(cache, cacheKey);
            return;
        }
        CacheData<T> cacheData = cacheData(
                cacheValue,
                expire,
                timeUnit
        );
//        if (!CommonTools.isEnvProduct()) {
//            log.debug(
//                    "■■■■■■ CACHE PUT [LOCAL] - {} ■■■■■■\n{}\n{}",
//                    cacheName,
//                    cacheKey,
//                    JsonUtil.toJSONString(cacheData.getTables())
//            );
//        }
        put(
                cache,
                cacheKey,
                cacheData
        );
        if (CommonUtils.isEmpty(cacheData.getTables())) {
            return;
        }
        initCacheTables(cacheData.getTables());
    }

    /**
     * 获取本地缓存
     *
     * @param cacheName
     * @param cacheKey
     * @param <T>
     * @return
     */
    public static <T> T getLocalCache(String cacheName, Object cacheKey) {
        if (ObjectUtils.isEmpty(cacheKey)) {
            return null;
        }
        if (StrUtils.isEmpty(cacheName)) {
            cacheName = CoreProperties.CACHE_NAME;
        }
        Cache cache = getCache(cacheName);
        if (ObjectUtils.isEmpty(cache)) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = valueWrapper(cache, cacheKey);
        if (ObjectUtils.isEmpty(valueWrapper)) {
            return null;
        }
        @SuppressWarnings("unchecked")
        CacheData<T> data = (CacheData<T>) valueWrapper.get();
        if (ObjectUtils.isEmpty(data)) {
            return null;
        }
        return validateCache(data);
    }

    public static void deleteLocalCache(String cacheName, Object cacheKey) {
        if (ObjectUtils.isEmpty(cacheKey)) {
            return;
        }
        if (StrUtils.isEmpty(cacheName)) {
            cacheName = CoreProperties.CACHE_NAME;
        }
        Cache cache = getCache(cacheName);
        if (ObjectUtils.isEmpty(cache)) {
            return;
        }
        evict(cache, cacheKey);
    }


}
