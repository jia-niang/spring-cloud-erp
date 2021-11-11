package com.kabunx.erp.exception;

/**
 * 统一的异常枚举
 */
public enum ExceptionEnum {
    SUCCESS(0, "操作成功"),
    FAILED(400000, "操作失败"),
    VALIDATE_FAILED(422000, "参数检验失败"),
    UNAUTHORIZED(401000, "暂未登录或token已经过期"),
    FORBIDDEN(403000, "没有相关权限");

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
