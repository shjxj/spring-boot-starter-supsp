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
public enum ShowStatus implements BaseEnum {
    none("none", "未设置"),
    show("show", "显示"),
    hidden("hidden", "隐藏");

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, ShowStatus> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (ShowStatus val : EnumSet.allOf(ShowStatus.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static ShowStatus getByType(String type) {
        if (!CODE_MAP.containsKey(type)) {
            return null;
        }
        return CODE_MAP.get(type);
    }

}
