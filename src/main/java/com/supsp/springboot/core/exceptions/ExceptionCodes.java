package com.supsp.springboot.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCodes {
    /**
     * 系统繁忙，请稍候再试
     */
    SYSTEM_ERROR(10500, "系统繁忙，请稍候再试"),

    /**
     * 操作失败
     */
    OPERATION_FAILURE(10500, "操作失败，请稍候再试"),

    /**
     * 请求参数不匹配
     */
    INVALID_REQUEST(10400, "请求参数不匹配"),

    /**
     * 参数错误
     */
    PARAM_ERROR(10400, "参数错误"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(10404, "数据不存在"),

    /**
     * 数据有误
     */
    DATA_ERROR(10404, "数据有误"),

    /**
     * 数据已存在
     */
    DATA_ALREADY_EXISTS(10404, "数据已存在"),

    /**
     * 错误请求
     */
    BAD_REQUEST(10400, "错误请求"),

    /**
     * 不支持此操作
     */
    REQUEST_ERROR(10400, "不支持此操作"),

    /**
     * 缺少参数
     */
    LACK_PARAMS(10400, "缺少参数"),

    /**
     * 请求数据不能为空
     */
    PARAMS_EMPTY(10400, "请求数据不能为空"),

    /**
     * 请上传文件
     */
    FILE_EMPTY(10400, "请上传文件"),

    /**
     * 文件数据错误
     */
    FILE_ERROR(10400, "文件数据错误"),

    /**
     * 账号不存在
     */
    ACCOUNT_NOT_EXIST(10403, "账号不存在"),

    /**
     * 账号重复
     */
    DUPLICATE_ACCOUNT(10403, "账号重复"),

    /**
     * 账号被禁用
     */
    ACCOUNT_IS_DISABLED(10403, "账号被禁用"),

    /**
     * 验证码错误
     */
    INCORRECT_CREDENTIALS(10403, "账号或密码错误"),

    /**
     * 账号或密码错误
     */
    INCORRECT_YZMCODE(10403, "账号或密码错误"),

    /**
     * 账号或密码错误
     */
    INCORRECT_CREDENTIALS_OLD(10403, "账号旧密码错误"),

    /**
     * 账号未登录
     */
    NOT_LOGIN_IN(10403, "账号未登录"),

    /**
     * 没有权限
     */
    UNAUTHORIZED(10403, "没有权限"),

    /**
     * 账号异常
     */
    ACCOUNT_ERROR(10403, "账号异常"),

    /**
     * 无权限
     */
    NO_AUTH(10403, "无权限"),

    /**
     * 签名错误
     */
    SIGN_ERROR(10401, "签名错误");

    /**
     * 类型
     */
    private final int code;
    /**
     * 说明
     */
    private final String message;
}
