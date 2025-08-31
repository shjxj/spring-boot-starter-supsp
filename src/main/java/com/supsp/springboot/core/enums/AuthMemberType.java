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
public enum AuthMemberType implements BaseEnum {
    /**
     * none -
     */
    none("none", "-"),
    /**
     * admin 管理员 AdminAuthAccount
     */
    admin("admin", "管理员"),
    /**
     * tenant 租户 TenantAuthAccount
     */
    tenant("tenant", "租户"),
    /**
     * merchant 商户 merchantAuthAccount
     */
    merchant("merchant", "商户"),
    /**
     * consumer 消费者 ConsumerAuthAccount
     */
    consumer("consumer", "消费者"),
    /**
     * API ApiAuthAccount
     */
    api("api", "API");

    @EnumValue
    private final String code;
    private final String desc;

    private static final Map<String, AuthMemberType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (AuthMemberType val : EnumSet.allOf(AuthMemberType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static AuthMemberType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
