package com.kabunx.erp.exception;

/**
 * 统一的异常枚举
 * 建议使用10位统一错误码，成功表示为S200
 * E01001B001
 * E 统一异常标记
 * 01 服务提供商（01网关，02认证，03用户, 99数据库...）
 * 001 功能领域
 * B 错误类型 （P 参数错误，B 业务错误, S 系统错误）
 * 404 配置错误码 http status
 */
public enum ExceptionEnum {
    SUCCESS("S200", "操作成功"),
    UNDEFINED("E00001S001", "未定义的异常"),
    FAILED("E00002S001", "操作失败"),
    NOT_FOUND("E00004S404", "资源不存在"),
    VALIDATE_FAILED("E00001P422", "参数检验失败"),
    CONVERSION("E00000P400", "超文本传输格式错误"),
    DB_FAILED("E99001S400", "数据操作失败"),
    FORBIDDEN("E00000B403", "没有相关权限"),
    UNAUTHORIZED("E01002P401", "暂未登录或token已经过期"),
    FALLBACK("E01001S503", "服务不可用"),
    GATEWAY_NOT_FOUND("E01000S404", "资源不存在"),
    GATEWAY_FAILED("E01000S503", "网关不可用");

    private final String code;
    private final String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
