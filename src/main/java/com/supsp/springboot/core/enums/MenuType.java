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
public enum MenuType implements BaseEnum {
    module("module", "模块"),
    controller("controller", "控制器"),
    action("action", "操作");

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, MenuType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (MenuType val : EnumSet.allOf(MenuType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static MenuType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
