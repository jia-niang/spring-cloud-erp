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
public class JsonResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public JsonResponse(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static <T> JsonResponse<T> withFallbackError() {
        return new JsonResponse<>(false, ExceptionEnum.FALLBACK.getCode(), ExceptionEnum.FALLBACK.getMessage());
    }

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>(true, ExceptionEnum.SUCCESS.getCode(), ExceptionEnum.SUCCESS.getMessage(), data);
    }

    public static <T> JsonResponse<T> success() {
        return success(null);
    }

    public static <T> JsonResponse<T> failed(String code, String message) {
        return new JsonResponse<>(false, code, message);
    }

    public static <T> JsonResponse<T> failed(String message) {
        return failed(ExceptionEnum.FAILED.getCode(), message);
    }

    public static <T> JsonResponse<T> failed(ExceptionEnum exceptionEnum) {
        return failed(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    public static <T> JsonResponse<T> error(T data) {
        return new JsonResponse<>(false, ExceptionEnum.NOT_FOUND.getCode(), ExceptionEnum.NOT_FOUND.getMessage(), data);
    }

    public static <T> JsonResponse<T> error() {
        return failed(ExceptionEnum.NOT_FOUND);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> JsonResponse<T> validateFailed() {
        return failed(ExceptionEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> JsonResponse<T> validateFailed(T errors) {
        return new JsonResponse<>(false, ExceptionEnum.VALIDATE_FAILED.getCode(), ExceptionEnum.VALIDATE_FAILED.getMessage(), errors);
    }

    /**
     * 未登录返回结果
     */
    public static <T> JsonResponse<T> unauthorized(T data) {
        return new JsonResponse<>(false, ExceptionEnum.UNAUTHORIZED.getCode(), ExceptionEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> JsonResponse<T> forbidden(T data) {
        return new JsonResponse<>(false, ExceptionEnum.FORBIDDEN.getCode(), ExceptionEnum.FORBIDDEN.getMessage(), data);
    }

    public boolean hasFallbackError() {
        return ExceptionEnum.FALLBACK.getCode().equals(code);
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

    public boolean dataIsNull() {
        return data == null;
    }

    public boolean dataIsNotNull() {
        return data != null;
    }

    public boolean available() {
        return isSuccess() && dataIsNotNull();
    }

    public boolean unavailable() {
        return isNotSuccess() || dataIsNull();
    }
}
