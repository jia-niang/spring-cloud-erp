package com.kabunx.erp.exception;

/**
 * 统一的异常枚举
 * 建议使用10位统一错误码，成功表示为S200
 * E01001B001
 * E 统一异常标记
 * 01 服务提供商
 * 001 功能领域
 * B 错误类型 P 参数错误，B 业务错误
 * 001 配置错误码
 */
public enum ExceptionEnum {
    SUCCESS(0, "操作成功"),
    UNDEFINED(400000, "未定义的异常"),
    FAILED(400000, "操作失败"),
    ERROR(404000, "资源不存在"),
    CONVERSION(400000, "超文本传输格式错误"),
    DB_FAILED(400100, "数据操作失败"),
    VALIDATE_FAILED(422000, "参数检验失败"),
    UNAUTHORIZED(401000, "暂未登录或token已经过期"),
    FORBIDDEN(403000, "没有相关权限"),
    FALLBACK(500000, "服务不可用"),
    GATEWAY_NOT_FOUND(500404, "资源不存在"),
    GATEWAY_FAILED(500001, "网关不可用");

    private final Integer code;
    private final String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
