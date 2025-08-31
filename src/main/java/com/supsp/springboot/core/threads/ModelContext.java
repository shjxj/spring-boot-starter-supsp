package com.supsp.springboot.core.threads;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.interfaces.IData;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ModelContext implements IData {

    @Serial
    private static final long serialVersionUID = 7792217921471357606L;

    private static final ThreadLocal<Map<String, List<String>>> MODEL_CONTEXT = new ThreadLocal<>();

    /**
     * 判断线程map是否为空，为空就添加一个map
     *
     * @return
     */
    protected static Map<String, List<String>> getLocalMap() {
        Map<String, List<String>> map = MODEL_CONTEXT.get();
        if (map == null) {
            map = new HashMap<>(10);
            map.put(Constants.SQL_STATEMENT_INSERT, new ArrayList<>());
            map.put(Constants.SQL_STATEMENT_UPDATE, new ArrayList<>());
            map.put(Constants.SQL_STATEMENT_DELETE, new ArrayList<>());
            MODEL_CONTEXT.set(map);
        }
        return map;
    }

    /**
     * 把数据添加到线程map中
     *
     * @param key
     * @param value
     */
    protected static void set(String key, String value) {
        if (StrUtils.isBlank(value)) {
            return;
        }
        if (key == null) {
            return;
        }
        Map<String, List<String>> map = getLocalMap();
        if (map.containsKey(key)) {
            return;
        }
        map.get(key).add(value);
    }

    /**
     * 获得线程map中的数据
     *
     * @param key
     * @return
     */
    protected static List<String> get(String key) {
        if (key == null) {
            return null;
        }
        Map<String, List<String>> map = getLocalMap();
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
    protected static void delete(String key) {
        if (key == null) {
            return;
        }
        try {
            Map<String, List<String>> map = getLocalMap();
            if (CommonUtils.isNotEmpty(map)) {
                map.put(key, new ArrayList<>());
            }
        } catch (Exception e) {
            log.error("exception message", e);
        }
    }

    public static List<String> getInsert() {
        return get(Constants.SQL_STATEMENT_INSERT);
    }

    public static List<String> getUpdate() {
        return get(Constants.SQL_STATEMENT_UPDATE);
    }

    public static List<String> getDelete() {
        return get(Constants.SQL_STATEMENT_DELETE);
    }

    public static void setInsert(String value) {
        set(Constants.SQL_STATEMENT_INSERT, value);
    }

    public static void setUpdate(String value) {
        set(Constants.SQL_STATEMENT_UPDATE, value);
    }

    public static void setDelete(String value) {
        set(Constants.SQL_STATEMENT_DELETE, value);
    }

    public static void cleanInsert() {
        delete(Constants.SQL_STATEMENT_INSERT);
    }

    public static void cleanUpdate() {
        delete(Constants.SQL_STATEMENT_UPDATE);
    }

    public static void cleanDelete() {
        delete(Constants.SQL_STATEMENT_DELETE);
    }

    /**
     * 清空线程map中的数据
     */
    public static void remove() {
        MODEL_CONTEXT.remove();
    }
}
