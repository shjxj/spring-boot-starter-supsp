package com.supsp.springboot.core.enums;

import com.supsp.springboot.core.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum LoginType implements BaseEnum {
    account("account", "登录账号"),
    mobile("mobile", "手机验证码"),
    qr("qr", "扫码");

    private final String code;

    private final String desc;

    private static final Map<String, LoginType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (LoginType val : EnumSet.allOf(LoginType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static LoginType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
