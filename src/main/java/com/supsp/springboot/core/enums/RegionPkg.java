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
public enum RegionPkg implements BaseEnum {
    ZH("ZH", "中国"),
    USA("USA", "美国");

    /**
     * 编码
     */
    @EnumValue
    private final String code;

    /**
     * 说明
     */
    private final String desc;

    private static final Map<String, RegionPkg> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (RegionPkg val : EnumSet.allOf(RegionPkg.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static RegionPkg getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
