package com.supsp.springboot.core.threads;

import com.supsp.springboot.core.interfaces.IData;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ConsumerData implements IData {
    @Serial
    private static final long serialVersionUID = 8562168563719278305L;

    private static final ThreadLocal<Map<String, Object>> CONSUMER_DATA = new ThreadLocal<>();

    /**
     * 判断线程map是否为空，为空就添加一个map
     *
     * @return
     */
    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = CONSUMER_DATA.get();
        if (map == null) {
            map = new HashMap<>(10);
            CONSUMER_DATA.set(map);
        }
        return map;
    }

    /**
     * 把数据添加到线程map中
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        if (StrUtils.isBlank(key)) {
            return;
        }
        Map<String, Object> map = getLocalMap();
        map.put(key, value);
    }

    /**
     * 获得线程map中的数据
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T get(String key) {
        try {
            if (StrUtils.isBlank(key)) {
                return null;
            }
            Map<String, Object> map = getLocalMap();
            if (CommonUtils.isNotEmpty(map) && map.containsKey(key)) {
                return (T) map.get(key);
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
            Map<String, Object> map = getLocalMap();
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
        CONSUMER_DATA.remove();
    }
}
