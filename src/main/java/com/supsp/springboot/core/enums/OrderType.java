package com.supsp.springboot.core.enums;

import com.supsp.springboot.core.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum OrderType implements BaseEnum {
    /**
     * 倒序
     */
    descend("desc", "倒序"),

    /**
     * 正序
     */
    ascend("asc", "正序");

    /**
     * 类型
     */
    private final String code;
    /**
     * 说明
     */
    private final String desc;

    private static final Map<String, OrderType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (OrderType val : EnumSet.allOf(OrderType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static OrderType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }

}
