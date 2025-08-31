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
public enum SysApp implements BaseEnum {
    normal("normal", "-"),
    sys("sys", "系统"),
    ums("ums", "用户"),
    examine("examine", "考核"),
    daemon("daemon", "后台");

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, SysApp> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (SysApp val : EnumSet.allOf(SysApp.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static SysApp getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }

}
