package com.supsp.springboot.core.threads;

import com.github.yulichang.toolkit.StrUtils;
import com.supsp.springboot.core.interfaces.IData;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.vo.CacheTable;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CacheTables implements IData {
    @Serial
    private static final long serialVersionUID = 8325016874071067214L;

    private static final ThreadLocal<Map<String, CacheTable>> CACHE_TABLES = new ThreadLocal<>();

    /**
     * 判断线程map是否为空，为空就添加一个map
     *
     * @return
     */
    public static Map<String, CacheTable> getLocalMap() {
        Map<String, CacheTable> map = CACHE_TABLES.get();
        if (map == null) {
            map = new HashMap<>(10);
            CACHE_TABLES.set(map);
        }
        return map;
    }

    /**
     * 把数据添加到线程map中
     *
     * @param key
     * @param value
     */
    public static void set(String key, CacheTable value) {
        if (StrUtils.isBlank(key)) {
            return;
        }
        Map<String, CacheTable> map = getLocalMap();
        map.put(key, value);
    }

    /**
     * 获得线程map中的数据
     *
     * @param key
     * @return
     */
    public static CacheTable get(String key) {
        try {
            if (StrUtils.isBlank(key)) {
                return null;
            }
            Map<String, CacheTable> map = getLocalMap();
            if (CommonUtils.isNotEmpty(map) && map.containsKey(key)) {
                return map.get(key);
            }
            return null;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return null;
    }

    /**
     * 移除线程map中的数据
     *
     * @param key
     */
    public static void delete(String key) {
        if (StrUtils.isBlank(key)) {
            return;
        }
        try {
            Map<String, CacheTable> map = getLocalMap();
            if (CommonUtils.isNotEmpty(map)) {
                map.remove(key);
            }
        } catch (Exception e) {
            log.error("exception message", e);
        }
    }

    /**
     * 清空线程map中的数据
     */
    public static void remove() {
        CACHE_TABLES.remove();
    }
}
