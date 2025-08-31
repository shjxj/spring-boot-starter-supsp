package com.supsp.springboot.core.consts;

import com.supsp.springboot.core.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.regex.Pattern;

@Slf4j
public class ValidateMatch {
    public static boolean isMatch(Pattern pattern, String str) {
        return StrUtils.isNotBlank(str) && pattern.matcher(str).matches();
    }

    /**
     * 正则：编码
     */
    public static final String REGEX_BASE_CODE = "^[A-Za-z0-9]{4,32}$";
    public static final Pattern PATTERN_BASE_CODE = Pattern.compile(REGEX_BASE_CODE);
    public static final String MSG_BASE_CODE = "编码仅支持大小写字母,长度4到32位";

    public static boolean isBaseCode(String code) {
        if (StrUtils.isBlank(code)) {
            return false;
        }
        return PATTERN_BASE_CODE.matcher(code).matches();
    }

    /**
     * 正则：编码
     */
    public static final String REGEX_SIMPLE_CODE = "^[A-Za-z0-9_]{3,32}$";
    public static final Pattern PATTERN_SIMPLE_CODE = Pattern.compile(REGEX_BASE_CODE);
    public static final String MSG_SIMPLE_CODE = "编码仅支持大小写字母数字及(_)一种特殊符号,长度3到32位";

    public static boolean isSimpleCode(String code) {
        if (StrUtils.isBlank(code)) {
            return false;
        }
        return isMatch(PATTERN_SIMPLE_CODE, code);
    }


    public static final String REGEX_BASE_KEY = "^[A-Za-z0-9._:/-]{2,64}$";
    public static final Pattern PATTERN_BASE_KEY = Pattern.compile(REGEX_BASE_KEY);
    public static final String MSG_BASE_KEY = "键名由大小写字母加数字及(.:/-)四种特殊字符组成,长度2-64位";

    public static boolean isBaseKey(String code) {
        if (StrUtils.isBlank(code)) {
            return false;
        }
        return PATTERN_BASE_KEY.matcher(code).matches();
    }

    /**
     * 正则：固定电话号码,可带区号,然后至少6,8位数字
     */
    public static final String REGEX_TEL = "^(\\d{3,4}-)?\\d{6,8}$";
    public static final Pattern PATTERN_TEL = Pattern.compile(REGEX_TEL);
    public static final String MSG_TEL = "电话号码有误";

    public static boolean isTel(String str) {
        return isMatch(PATTERN_TEL, str);
    }

    /**
     * 正则：手机号（精确）, 已知3位前缀＋8位数字
     * <p>
     * 移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188
     * </p>
     * <p>
     * 联通：130、131、132、145、155、156、175、176、185、186
     * </p>
     * <p>
     * 电信：133、153、173、177、180、181、189
     * </p>
     * <p>
     * 全球星：1349
     * </p>
     * <p>
     * 虚拟运营商：170
     * </p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";
    public static final Pattern PATTERN_MOBILE_EXACT = Pattern.compile(REGEX_MOBILE_EXACT);
    public static final String MSG_MOBILE_EXACT = "编码仅支持大小写字母,长度4到32位";

    public static boolean isMobileExact(String code) {
        if (StrUtils.isBlank(code)) {
            return false;
        }
        return PATTERN_MOBILE_EXACT.matcher(code).matches();
    }

    /**
     * 正则：手机号（简单）, 1字头＋10位数字即可.
     */
    public static final String REGEX_MOBILE_SIMPLE = "^1\\d{10}$";
    public static final Pattern PATTERN_MOBILE_SIMPLE = Pattern.compile(REGEX_MOBILE_SIMPLE);
    public static final String MSG_MOBILE_SIMPLE = "手机号有误";

    public static boolean isMobileSimple(String str) {
        return isMatch(PATTERN_MOBILE_SIMPLE, str);
    }

    /**
     * 正则：手机号（简单）, 1字头＋10位数字即可.
     */
    public static final String REGEX_DEVICE_CODE = "^\\d{12,18}$";
    public static final Pattern PATTERN_DEVICE_CODE = Pattern.compile(REGEX_DEVICE_CODE);
    public static final String MSG_DEVICE_CODE = "设备码有误";

    public static boolean isDeviceCode(String str) {
        return isMatch(PATTERN_DEVICE_CODE, str);
    }

    /**
     * 正则：身份证号码15位, 数字且关于生日的部分必须正确
     */
    public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    public static final Pattern PATTERN_ID_CARD15 = Pattern.compile(REGEX_ID_CARD15);

    /**
     * 正则：身份证号码18位, 数字且关于生日的部分必须正确
     */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    public static final Pattern PATTERN_ID_CARD18 = Pattern.compile(REGEX_ID_CARD18);
    public static final String MSG_ID_CARD = "身份证号码有误";

    public static boolean isIdCard(String str) {
        return isMatch(PATTERN_ID_CARD15, str) || isMatch(PATTERN_ID_CARD18, str);
    }

    /**
     * 包名
     */
    public static final String REGEX_PKG_NAME = "^[A-Za-z0-9.]{2,128}$";
    public static final Pattern PATTERN_PKG_NAME = Pattern.compile(REGEX_PKG_NAME);
    public static final String MSG_PKG_NAME = "包名由大小写字母加数字及(.)一种特殊字符组成,长度2-128位";

    public static boolean isPkgName(String string) {
        if (StrUtils.isBlank(string)) {
            return false;
        }
        return PATTERN_PKG_NAME.matcher(string).matches();
    }

    /**
     * 权限标识
     */
    public static final String REGEX_PERMISSION = "^[A-Za-z0-9_:/-]{2,32}$";
    public static final Pattern PATTERN_PERMISSION = Pattern.compile(REGEX_PERMISSION);
    public static final String MSG_PERMISSION = "权限标识仅支持大小写字母,数字以及(_:-)三种字符,长度3到32位";

    public static boolean isPermission(String string) {
        if (StrUtils.isBlank(string)) {
            return false;
        }
        return PATTERN_PERMISSION.matcher(string).matches();
    }

    /**
     * 对象名称
     */
    public static final String REGEX_OBJECT_NAME = "^[A-Za-z0-9._]{3,128}$";
    public static final Pattern PATTERN_OBJECT_NAME = Pattern.compile(REGEX_OBJECT_NAME);
    public static final String MSG_OBJECT_NAME = "对象名称由大小写字母加数字及(._)两种特殊字符组成,长度3-128位";

    public static boolean isObjectName(String string) {
        if (StrUtils.isBlank(string)) {
            return false;
        }
        return PATTERN_OBJECT_NAME.matcher(string).matches();
    }

    /**
     * 登录账号
     */
    public static final String REGEX_LOGIN_ACCOUNT = "^[A-Za-z0-9·._/-]{5,32}$";
    public static final Pattern PATTERN_LOGIN_ACCOUNT = Pattern.compile(REGEX_LOGIN_ACCOUNT);
    public static final String MSG_LOGIN_ACCOUNT = "账号由大小写字母加数字及(·._-)四种特殊字符组成,长度5-32位";

    public static boolean isLoginAccount(String string) {
        if (StrUtils.isBlank(string)) {
            return false;
        }
        return PATTERN_LOGIN_ACCOUNT.matcher(string).matches();
    }

    /**
     * 正则：邮箱, 有效字符(不支持中文), 且中间必须有@,后半部分必须有.
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final Pattern PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);
    public static final String MSG_EMAIL = "邮箱有误";

    public static boolean isEmail(String str) {
        return isMatch(PATTERN_EMAIL, str);
    }

    /**
     * 正则：统一社会信用代码 15位
     */
    public static final String REGEX_LICENSE_CARD_15 = "^[1-9]\\d{15}$";
    public static final Pattern PATTERN_LICENSE_CARD_15 = Pattern.compile(REGEX_LICENSE_CARD_15);

    /**
     * 正则：统一社会信用代码 18位
     */
    public static final String REGEX_LICENSE_CARD_18 = "^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$";
    public static final Pattern PATTERN_LICENSE_CARD_18 = Pattern.compile(REGEX_LICENSE_CARD_18);
    public static final String MSG_LICENSE_CARD = "统一社会信用代码有误";

    public static boolean isLicenseCard(String str) {
        return isMatch(PATTERN_LICENSE_CARD_15, str.toUpperCase(Locale.ROOT)) || isMatch(PATTERN_LICENSE_CARD_18, str.toUpperCase(Locale.ROOT));
    }

    /**
     * 正则：URL, 必须有"://",前面必须是英文,后面不能有空格
     */
    public static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    public static final Pattern PATTERN_URL = Pattern.compile(REGEX_URL);
    public static final String MSG_URL = "网址有误";

    public static boolean isUrl(String str) {
        return isMatch(PATTERN_URL, str);
    }

    /**
     * 正则：yyyy-MM-dd格式的日期校验,已考虑平闰年
     */
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    public static final Pattern PATTERN_DATE = Pattern.compile(REGEX_DATE);
    public static final String MSG_DATE = "日期有误";

    public static boolean isDate(String str) {
        return isMatch(PATTERN_DATE, str);
    }

    /**
     * 正则：IP地址
     */
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    public static final Pattern PATTERN_IP = Pattern.compile(REGEX_IP);
    public static final String MSG_IP = "IP有误";

    public static boolean isIp(String str) {
        return isMatch(PATTERN_IP, str);
    }

    /**
     * 正则：车牌号
     */
    public static final String REGEX_CAR = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{3,4}[A-Z0-9挂学警港澳]{1}$";
    public static final Pattern PATTERN_CAR = Pattern.compile(REGEX_CAR);
    public static final String MSG_CAR = "车牌号有误";

    public static boolean isCar(String str) {
        return isMatch(PATTERN_CAR, str);
    }


    /**
     * 正则：单选题
     */
    public static final String REGEX_QUESTION_SINGLE_CHOICE = "^[A-Za-z]{1}$";
    public static final Pattern PATTERN_QUESTION_SINGLE_CHOICE = Pattern.compile(REGEX_QUESTION_SINGLE_CHOICE);
    public static final String MSG_QUESTION_SINGLE_CHOICE = "单选题答案仅为一个英文字母";

    public static boolean isQuestionSingleChoice(String code) {
        if (StrUtils.isBlank(code)) {
            return false;
        }
        return PATTERN_QUESTION_SINGLE_CHOICE.matcher(code).matches();
    }

    public static final String REGEX_QUESTION_MULTIPLE_CHOICE = "^[A-Za-z]{1,10}$";
    public static final Pattern PATTERN_QUESTION_MULTIPLE_CHOICE = Pattern.compile(REGEX_QUESTION_MULTIPLE_CHOICE);
    public static final String MSG_QUESTION_MULTIPLE_CHOICE = "多选题答案仅为英文字母";

    public static boolean isQuestionMultipleChoice(String code) {
        if (StrUtils.isBlank(code)) {
            return false;
        }
        return PATTERN_QUESTION_MULTIPLE_CHOICE.matcher(code).matches();
    }
}
