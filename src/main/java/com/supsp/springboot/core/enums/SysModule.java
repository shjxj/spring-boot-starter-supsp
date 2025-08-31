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
public enum SysModule implements BaseEnum {
    normal("normal", "-"),
    base("base", ""),
    admin("admin", "管理端"),
    tenant("tenant", "租户端"),
    merchant("merchant", "商户端"),
    consumer("consumer", "用户者"),
    api("api", "API");

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, SysModule> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (SysModule val : EnumSet.allOf(SysModule.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static SysModule getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
