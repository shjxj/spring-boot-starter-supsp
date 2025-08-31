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
public enum BooleanValue implements BaseEnum {

    Null("Null", null),
    False("False", Boolean.FALSE),
    True("True", Boolean.TRUE);

    @EnumValue
    private final String code;

    private final Boolean value;

    private static final Map<String, BooleanValue> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (BooleanValue val : EnumSet.allOf(BooleanValue.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static BooleanValue getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
