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
public enum EnableStatus implements BaseEnum {
    none("none", "未设置"),
    enable("enable", "启用"),
    disable("disable", "禁用");

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, EnableStatus> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (EnableStatus val : EnumSet.allOf(EnableStatus.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static EnableStatus getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
