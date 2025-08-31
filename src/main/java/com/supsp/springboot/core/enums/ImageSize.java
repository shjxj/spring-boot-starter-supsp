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
public enum ImageSize implements BaseEnum {
    /**
     * 原图
     */
    O("original", "原图"),
    /**
     * 大规格图
     */
    L("large", "大规格图"),
    /**
     * 中规格图
     */
    M("medium", "中规格图"),
    /**
     * 小规格图
     */
    S("small", "小规格图"),
    /**
     * 超小规格图
     */
    XS("xsmall", "超小规格图");

    /**
     * 类型
     */
    @EnumValue
    private final String code;
    /**
     * 说明
     */
    private final String desc;

    private static final Map<String, ImageSize> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (ImageSize val : EnumSet.allOf(ImageSize.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static ImageSize getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
