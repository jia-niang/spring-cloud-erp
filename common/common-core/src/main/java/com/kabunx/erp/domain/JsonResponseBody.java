package com.kabunx.erp.domain;

import com.kabunx.erp.exception.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static <T> JsonResponseBody<T> success() {
        return new JsonResponseBody<>();
    }

    public static <T> JsonResponseBody<T> success(T data) {
        return new JsonResponseBody<>(true, 0, "成功", data);
    }

    public static <T> JsonResponseBody<T> failed(Integer code, String message) {
        return new JsonResponseBody<>(false, code, message);
    }

    public static <T> JsonResponseBody<T> failed(String message) {
        return new JsonResponseBody<>(false, 400000, message);
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
     *
     * @param message 提示信息
     */
    public static <T> JsonResponseBody<T> validateFailed(String message) {
        return new JsonResponseBody<>(false, ExceptionEnum.VALIDATE_FAILED.getCode(), message, null);
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
