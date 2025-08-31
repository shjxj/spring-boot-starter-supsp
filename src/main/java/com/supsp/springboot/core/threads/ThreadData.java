package com.supsp.springboot.core.threads;

import com.supsp.springboot.core.interfaces.IData;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ThreadData implements IData {
    @Serial
    private static final long serialVersionUID = 8287353942536101899L;

    private static final ThreadLocal<Map<String, Object>> THREAD_DATA = new ThreadLocal<>();

    /**
     * 判断线程map是否为空，为空就添加一个map
     *
     * @return
     */
    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_DATA.get();
        if (map == null) {
            map = new HashMap<>(10);
            THREAD_DATA.set(map);
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

    // list
    public static <V> List<V> getList(String key) {
        if (StrUtils.isBlank(key)) {
            return null;
        }
        return get(key);
    }

    public static <V> void listAddValue(String key, V value) {
        if (StrUtils.isBlank(key) || value == null) {
            return;
        }
        List<V> list = getList(key);
        if (list == null) {
            list = new ArrayList<>();
        }
        CollectionUtils.addAll(list, value);
        set(key, list);
    }

    public static <V> void listAddValues(String key, List<V> values) {
        if (StrUtils.isBlank(key) || CommonUtils.isEmpty(values)) {
            return;
        }
        List<V> list = getList(key);
        if (list == null) {
            list = new ArrayList<>();
        }
        CollectionUtils.addAll(list, values);
        set(key, list);
    }

    public static <V> void listRemoveValue(String key, V value) {
        if (StrUtils.isBlank(key)) {
            return;
        }
        List<V> list = getList(key);
        if (list == null) {
            return;
        }
        list.remove(value);
        set(key, list);
    }

    public static <V> void listRemoveValues(String key, List<V> values) {
        if (StrUtils.isBlank(key) || CommonUtils.isEmpty(values)) {
            return;
        }
        List<V> list = getList(key);
        if (list == null) {
            return;
        }
        CollectionUtils.removeAll(list, values);
        set(key, list);
    }

    // map
    public static <V> Map<String, V> getMap(String key) {
        return get(key);
    }

    public static <V> void mapAddValue(String key, String mapKey, V value) {
        if (StrUtils.isBlank(key) || StrUtils.isBlank(mapKey)) {
            return;
        }
        Map<String, V> map = getMap(key);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(mapKey, value);
        set(key, map);
    }

    public static <V> void mapAddValues(String key, Map<String, V> values) {
        if (StrUtils.isBlank(key) || CommonUtils.isEmpty(values)) {
            return;
        }
        Map<String, V> map = getMap(key);
        if (map == null) {
            map = new HashMap<>();
        }
        map.putAll(values);
        set(key, map);
    }

    public static void mapRemoveByMapKey(String key, String mapKey) {
        if (StrUtils.isBlank(key) || StrUtils.isBlank(mapKey)) {
            return;
        }
        Map<String, ?> map = getMap(key);
        if (CommonUtils.isEmpty(map)) {
            return;
        }
        map.remove(mapKey);
        set(key, map);
    }

    public static void mapRemoveByMapKey(String key, List<String> mapKeys) {
        if (CommonUtils.isEmpty(mapKeys)) {
            return;
        }
        Map<String, ?> map = getMap(key);
        if (CommonUtils.isEmpty(map)) {
            return;
        }
        for (String k : mapKeys) {
            map.remove(k);
        }
        set(key, map);
    }

    /**
     * 清空线程map中的数据
     */
    public static void remove() {
        THREAD_DATA.remove();
    }
}
