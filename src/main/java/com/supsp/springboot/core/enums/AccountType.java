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
public enum AccountType implements BaseEnum {

    account("account", "登录账号"),
    ident("ident", "标识"),
    email("email", "邮箱"),
    mobile("mobile", "手机"),
    wechat("wechat", "微信"),
    alipay("alipay", "支付宝");

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, AccountType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (AccountType val : EnumSet.allOf(AccountType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static AccountType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
