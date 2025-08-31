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
public enum RegionType implements BaseEnum {
    // street community
    province("province", (short) 1, "省"),
    city("city", (short) 2, "市"),
    district("district", (short) 3, "区"),
    street("street", (short) 4, "街道"),
    community("community", (short) 5, "社区");

    @EnumValue
    private final String code;

    private final Short grade;

    private final String desc;

    private static final Map<String, RegionType> CODE_MAP = new ConcurrentHashMap<>();

    private static final Map<Short, RegionType> GRADE_MAP = new ConcurrentHashMap<>();

    static {
        for (RegionType val : EnumSet.allOf(RegionType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    static {
        for (RegionType val : EnumSet.allOf(RegionType.class)) {
            GRADE_MAP.put(val.getGrade(), val);
        }
    }

    public static RegionType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }

    public static RegionType getByGrade(Short grade) {
        if (grade == null || !GRADE_MAP.containsKey(grade)) {
            return null;
        }
        return GRADE_MAP.get(grade);
    }
}
