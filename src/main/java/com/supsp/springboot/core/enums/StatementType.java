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
public enum StatementType implements BaseEnum {
    None("None", "-"),
    Select("Select", "查询"),
    Insert("Insert", "新建"),
    Upsert("Upsert", "新建或更新"),
    Update("Update", "更新"),
    Delete("Delete", "删除"),
    Truncate("Truncate", "清空");

    @EnumValue
    private final String code;

    private final String name;

    private static final Map<String, StatementType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (StatementType val : EnumSet.allOf(StatementType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static StatementType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
