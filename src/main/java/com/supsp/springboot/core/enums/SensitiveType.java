package com.supsp.springboot.core.enums;

import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.supsp.springboot.core.base.BaseEnum;
import com.supsp.springboot.core.service.ISensitiveMaskService;
import com.supsp.springboot.core.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public enum SensitiveType implements BaseEnum {

    NONE("none", "不隐藏") {
        @Override
        public String maskData(String data) {
            // 默认原值
            return data;
        }
    },
    MOBILE("mobile", "手机号") {
        @Override
        public String maskData(String data) {
            //手机号前3位后4位脱敏，中间部分加*处理，比如：138****5678
            return DesensitizedUtil.mobilePhone(data);
        }
    },
    IDENTIFY("identify", "身份证号") {
        @Override
        public String maskData(String data) {
            return DesensitizedUtil.idCardNum(data, 3, 4);
        }
    },
    ORG_LICENSE("orgLicense", "企业统一社会信用代码") {
        @Override
        public String maskData(String data) {
            return CommonUtils.strHideMiddle(data, 6);
        }
    },
    BANKCARD("bankcard", "银行卡号") {
        @Override
        public String maskData(String data) {
            //银行卡号前4位后4位脱敏，中间部分加*处理，比如：6225 **** **** *** 0845
            return DesensitizedUtil.bankCard(data);
        }
    },
    EMAIL("email", "邮箱") {
        @Override
        public String maskData(String data) {
            //邮箱@符号后明文显示，@符号前的字符串，只显示第一个字符，其余加*处理，比如：a***********@test.com
            return DesensitizedUtil.email(data);
        }
    },
    ADDRESS("address", "地址") {
        @Override
        public String maskData(String data) {
            return DesensitizedUtil.address(data, 3);
        }
    },
    ACCOUNT("account", "账号") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return CommonUtils.strHideMiddle(data, 3);
        }
    },
    SID("sid", "SID") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return CommonUtils.strHideTail(data, 16);
        }
    },
    TOKEN("token", "toke") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return CommonUtils.strHideTail(data, 16);
        }
    },
    ID("id", "ID") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return CommonUtils.strHideTail(data, 6);
        }
    },
    SECRET("secret", "secret") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return "******";
        }
    },
    CIPHERTEXT("ciphertext", "密文") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return "******";
            //return CommonUtils.strHideTail(data, 16);
        }
    },
    CERT("cert", "证书") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return """
                    -----BEGIN CERTIFICATE-----
                    ******
                    -----END CERTIFICATE-----""";
        }
    },
    PRIVKEY("privkey", "私钥") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return """
                    -----BEGIN PRIVATE KEY-----
                    ******
                    -----END PRIVATE KEY-----""";
        }
    },
    PUBKEY("PUBKEY", "公钥") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return """
                    -----BEGIN PUBLIC KEY-----
                    ******
                    -----END PUBLIC KEY-----""";
        }
    },
    PWD_VALUE("pwdValue", "密码值") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return CommonUtils.strHideTail(data, 16);
        }
    },
    CHINESE_NAME("chineseName", "姓名") {
        @Override
        public String maskData(String data) {
            return DesensitizedUtil.chineseName(data);
        }
    },
    NAME("name", "姓名") {
        @Override
        public String maskData(String data) {
         //   return CommonUtils.strHideTail(data, 2);
            return DesensitizedUtil.chineseName(data);
        }
    },
    ORG_NAME("orgName", "企业名称") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return CommonUtils.strHideMiddle(data, 2);
        }
    },
    STORE_NAME("storeName", "门店名称") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return CommonUtils.strHideMiddle(data, 2);
        }
    },
    SHOP_NAME("shopName", "店铺名称") {
        @Override
        public String maskData(String data) {
            if (StrUtil.isBlank(data)) {
                return "";
            }
            return CommonUtils.strHideMiddle(data, 4);
        }
    },
    IPV4("ipv4", "ipv4") {
        @Override
        public String maskData(String data) {
            return DesensitizedUtil.ipv4(data);
        }
    },
    IPV6("ipv6", "ipv6") {
        @Override
        public String maskData(String data) {
            return DesensitizedUtil.ipv6(data);
        }
    },
    CAR_LICENSE("carLicense", "车牌") {
        @Override
        public String maskData(String data) {
            return DesensitizedUtil.carLicense(data);
        }
    },
    FIRST_MASK("firstMask", "首字") {
        @Override
        public String maskData(String data) {
            return DesensitizedUtil.firstMask(data);
        }
    },
    DEFAULT("default", "默认") {
        @Override
        public String maskData(String data) {
            // 默认原值
            return data;
        }
    },
    CUSTOM("custom", "自定义") {
        @Override
        public String maskData(
                String data,
                ISensitiveMaskService customMaskService
        ) {
            // 自定
            return customMaskService.maskData(data);
        }
    };

    @EnumValue
    private final String code;

    private final String desc;

    private static final Map<String, SensitiveType> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (SensitiveType val : EnumSet.allOf(SensitiveType.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static SensitiveType getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }

    /**
     * 遮挡敏感数据
     *
     * @param data
     * @return
     */
    public String maskData(String data) {
        return data;
    }

    public String maskData(String data, ISensitiveMaskService customMaskService) {
        return customMaskService.maskData(data);
    }

}
