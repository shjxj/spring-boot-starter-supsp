package com.supsp.springboot.core.enums;

import com.supsp.springboot.core.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum CacheType implements BaseEnum {
    FULL("FULL", "存取"),
    PUT("PUT", "只存"),
    DELETE("DELETE", "删除");

    private final String code;

    private final String description;

    private static final Map<String, CacheType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (CacheType val : EnumSet.allOf(CacheType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static CacheType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }

}
