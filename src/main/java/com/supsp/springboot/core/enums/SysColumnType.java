package com.supsp.springboot.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum SysColumnType implements IColumnType {

    ORG_ROLE("OrgRole", "com.supsp.springboot.core.enums.OrgRole"),
    ACCOUNT_TYPE("AccountType", "com.supsp.springboot.core.enums.AccountType"),
    MEMBER_TYPE("AuthMemberType", "com.supsp.springboot.core.enums.AuthMemberType"),

    IS_SYSTEM("Short", (String) null),
    IS_DEFAULT("Short", (String) null),
    ENABLE_STATUS("EnableStatus", "com.supsp.springboot.core.enums.EnableStatus"),
    SHOW_STATUS("ShowStatus", "com.supsp.springboot.core.enums.ShowStatus"),
    AUDIT_STATUS("AuditStatus", "com.supsp.springboot.core.enums.AuditStatus"),
    REPORT_STATUS("ReportStatus", "com.supsp.springboot.core.enums.ReportStatus"),
    ORDER_SORT("Integer", (String) null),

    CREATED_AT("LocalDateTime", "java.time.LocalDateTime"),
    CREATED_MEMBER_TYPE("AuthMemberType", "com.supsp.springboot.core.enums.AuthMemberType"),
    CREATED_MEMBER_ID("Long", (String) null),
    CREATED_MEMBER_NAME("String", (String) null),
    CREATED_MEMBER_ACCOUNT("String", (String) null),
    CREATED_MEMBER_IP("String", (String) null),
    CREATED_ORG_ID("Long", (String) null),
    CREATED_ORG_NAME("String", (String) null),
    CREATED_STORE_ID("Long", (String) null),
    CREATED_STORE_NAME("String", (String) null),

    UPDATED_AT("LocalDateTime", "java.time.LocalDateTime"),
    UPDATED_MEMBER_TYPE("AuthMemberType", "com.supsp.springboot.core.enums.AuthMemberType"),
    UPDATED_MEMBER_ID("Long", (String) null),
    UPDATED_MEMBER_NAME("String", (String) null),
    UPDATED_MEMBER_ACCOUNT("String", (String) null),
    UPDATED_MEMBER_IP("String", (String) null),
    UPDATED_ORG_ID("Long", (String) null),
    UPDATED_ORG_NAME("String", (String) null),
    UPDATED_STORE_ID("Long", (String) null),
    UPDATED_STORE_NAME("String", (String) null),

    DELETED("Short", (String) null),

    DELETED_AT("LocalDateTime", "java.time.LocalDateTime"),
    DELETED_MEMBER_TYPE("AuthMemberType", "com.supsp.springboot.core.enums.AuthMemberType"),
    DELETED_MEMBER_ID("Long", (String) null),
    DELETED_MEMBER_NAME("String", (String) null),
    DELETED_MEMBER_ACCOUNT("String", (String) null),
    DELETED_MEMBER_IP("String", (String) null),
    DELETED_ORG_ID("Long", (String) null),
    DELETED_ORG_NAME("String", (String) null),
    DELETED_STORE_ID("Long", (String) null),
    DELETED_STORE_NAME("String", (String) null);

    @EnumValue
    private final String type;

    private final String pkg;

    private static final Map<String, SysColumnType> TYPE_MAP = new ConcurrentHashMap<>();

    static {
        for (SysColumnType val : EnumSet.allOf(SysColumnType.class)) {
            TYPE_MAP.put(val.getType(), val);
        }
    }

    public static SysColumnType getByType(String type) {
        if (!TYPE_MAP.containsKey(type)) {
            return null;
        }
        return TYPE_MAP.get(type);
    }
}
