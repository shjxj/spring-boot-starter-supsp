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
public enum OrgAuthority implements BaseEnum {
    none("none", "未设置"),
    global("global", "全局"),
    region("region", "地区"),
    org("org", "组织"),
    store("store", "部门");

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, OrgAuthority> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (OrgAuthority val : EnumSet.allOf(OrgAuthority.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static OrgAuthority getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
