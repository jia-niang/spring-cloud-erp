package com.kabunx.erp.domain;

import com.kabunx.erp.exception.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 统一的响应结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponseBody<T> {
    private boolean success;
    private Integer code;
    private String message;
    private T data;

    public JsonResponseBody(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean hasFallbackError() {
        return ExceptionEnum.FALLBACK.getCode().equals(code);
    }

    public static <T> JsonResponseBody<T> withFallbackError() {
        return new JsonResponseBody<>(false, ExceptionEnum.FALLBACK.getCode(), ExceptionEnum.FALLBACK.getMessage());
    }

    public static <T> JsonResponseBody<T> success() {
        return new JsonResponseBody<>();
    }

    public static <T> JsonResponseBody<T> success(T data) {
        return new JsonResponseBody<>(true, ExceptionEnum.SUCCESS.getCode(), ExceptionEnum.SUCCESS.getMessage(), data);
    }

    public static <T> JsonResponseBody<T> failed(Integer code, String message) {
        return new JsonResponseBody<>(false, code, message);
    }

    public static <T> JsonResponseBody<T> failed(String message) {
        return new JsonResponseBody<>(false, ExceptionEnum.FAILED.getCode(), message);
    }

    public static <T> JsonResponseBody<T> failed(ExceptionEnum exceptionEnum) {
        return new JsonResponseBody<>(false, exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> JsonResponseBody<T> validateFailed() {
        return failed(ExceptionEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> JsonResponseBody<T> validateFailed(T errors) {
        return new JsonResponseBody<>(false, ExceptionEnum.VALIDATE_FAILED.getCode(), ExceptionEnum.VALIDATE_FAILED.getMessage(), errors);
    }

    /**
     * 未登录返回结果
     */
    public static <T> JsonResponseBody<T> unauthorized(T data) {
        return new JsonResponseBody<>(false, ExceptionEnum.UNAUTHORIZED.getCode(), ExceptionEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> JsonResponseBody<T> forbidden(T data) {
        return new JsonResponseBody<>(false, ExceptionEnum.FORBIDDEN.getCode(), ExceptionEnum.FORBIDDEN.getMessage(), data);
    }
}
