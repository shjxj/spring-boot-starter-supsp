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
public enum LogOperation implements BaseEnum {

    none("none", "-"),

    //
    passport("passport", "通行证"),

    //
    login("login", "登录"),
    logout("logout", "退出登录"),
    changePwd("changePwd", "修改密码"),
    profile("profile", "修改个人信息"),
    avatar("avatar", "修改头像"),

    //
    create("create", "新增"),
    edit("edit", "编辑"),

    enable("enable", "启用"),
    batchEnable("batchEnable", "批量启用"),

    disable("disable", "禁用"),
    batchDisable("batchDisable", "批量禁用"),

    show("show", "显示"),
    batchShow("batchShow", "批量显示"),

    hidden("hidden", "隐藏"),
    batchHidden("batchHidden", "批量隐藏"),

    setOrder("setOrder", "设置排序"),
    batchSetOrder("batchSetOrder", "批量设置排序"),

    saveTags("saveTags", "保存数据标签"),

    item("item", "子数据操作"),
    batch("batch", "批量数据操作"),

    examination("examination", "参加考试"),

    info("info", "提交信息"),

    submit("submit", "设置排序"),
    reject("reject", "驳回审核"),
    approved("approved", "通过审核"),
    invalid("invalid", "作废审核"),;

    @EnumValue
    private final String code;
    private final String desc;

    private static final Map<String, LogOperation> CODE_MAP = new ConcurrentHashMap<>();

    static {
        for (LogOperation val : EnumSet.allOf(LogOperation.class)) {
            CODE_MAP.put(val.getCode(), val);
        }
    }

    public static LogOperation getByCode(String code) {
        if (!CODE_MAP.containsKey(code)) {
            return null;
        }
        return CODE_MAP.get(code);
    }
}
