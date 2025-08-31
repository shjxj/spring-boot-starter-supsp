package com.supsp.springboot.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.supsp.springboot.core.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@AllArgsConstructor
public enum DictScene implements BaseEnum {
    none("none", "默认"),
    operation("operation", "运营"),
    application("application", "应用"),
    system("system", "系统"),
    dev("dev", "开发");

    @EnumValue
    private final String code;
    private final String desc;

    private static final Map<String, DictScene> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (DictScene val : EnumSet.allOf(DictScene.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static DictScene getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
