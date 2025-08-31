package com.supsp.springboot.core.utils;

import cn.hutool.extra.pinyin.PinyinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PinyinUtils {
    public static String pingYin(String src, String separator) {
        if (StrUtils.isBlank(src)) {
            return "";
        }
        return PinyinUtil.getPinyin(src, separator);
    }

    public static String pingYin(String src) {
        if (StrUtils.isBlank(src)) {
            return "";
        }
        return pingYin(src, " ");
    }

    public static String firstLetter(String src, String separator) {
        if (StrUtils.isBlank(src)) {
            return "";
        }
        return PinyinUtil.getFirstLetter(src, separator);
    }

    public static String firstLetter(String src) {
        if (StrUtils.isBlank(src)) {
            return "";
        }
        return firstLetter(src, "");
    }
}
