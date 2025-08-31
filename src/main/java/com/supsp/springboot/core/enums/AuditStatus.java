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
public enum AuditStatus implements BaseEnum {

    progress("progress", "数据录入"),
    ready("ready", "录入完成"),
    submit("submit", "待审"),
    reject("reject", "驳回"),
    approved("approved", "通过"),
    apply_invalid("apply_invalid", "申请作废"),
    invalid("invalid", "作废");

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, AuditStatus> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (AuditStatus val : EnumSet.allOf(AuditStatus.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static AuditStatus getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
