package com.supsp.springboot.core.threads;

import com.supsp.springboot.core.interfaces.IData;
import com.supsp.springboot.core.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SensitiveContext implements IData {

    @Serial
    private static final long serialVersionUID = 7792217921471357606L;

    private static final ThreadLocal<Map<Class<?>, Boolean>> SENSITIVE_CONTEXT = new ThreadLocal<>();

    /**
     * 判断线程map是否为空，为空就添加一个map
     *
     * @return
     */
    public static Map<Class<?>, Boolean> getLocalMap() {
        Map<Class<?>, Boolean> map = SENSITIVE_CONTEXT.get();
        if (map == null) {
            map = new HashMap<>(10);
            SENSITIVE_CONTEXT.set(map);
        }
        return map;
    }

    /**
     * 把数据添加到线程map中
     *
     * @param key
     * @param value
     */
    public static void set(Class<?> key, Boolean value) {
        if (key == null) {
            return;
        }
        Map<Class<?>, Boolean> map = getLocalMap();
        map.put(key, value);
    }

    /**
     * 获得线程map中的数据
     *
     * @param key
     * @return
     */
    public static Boolean get(Class<?> key) {
        if (key == null) {
            return null;
        }
        Map<Class<?>, Boolean> map = getLocalMap();
        if (CommonUtils.isNotEmpty(map) && map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    /**
     * 移除线程map中的数据
     *
     * @param key
     */
    public static void delete(Class<?> key) {
        if (key == null) {
            return;
        }
        try {
            Map<Class<?>, Boolean> map = getLocalMap();
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
        SENSITIVE_CONTEXT.remove();
    }
}
