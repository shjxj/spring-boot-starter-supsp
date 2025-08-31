package com.supsp.springboot.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.supsp.springboot.core.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum IgnoreValue implements BaseEnum {
    Null("null", null),
    False("false", false),
    True("true", true);

    @EnumValue
    private final String code;
    private final Boolean value;

    private static final Map<String, IgnoreValue> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (IgnoreValue val : EnumSet.allOf(IgnoreValue.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static IgnoreValue getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
