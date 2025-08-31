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
public enum FileType implements BaseEnum {
    image("image", "图片"),
    audio("audio", "音频"),
    video("video", "视频"),
    doc("doc", "文档"),
    file("file", "文件");

    /**
     * 编码
     */
    @EnumValue
    private final String code;

    /**
     * 说明
     */
    private final String desc;

    private static final Map<String, FileType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (FileType val : EnumSet.allOf(FileType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static FileType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
