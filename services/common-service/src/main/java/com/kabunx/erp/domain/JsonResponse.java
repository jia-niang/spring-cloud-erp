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
    private Integer code;
    private String message;
    private T data;

    public JsonResponse(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static <T> JsonResponse<T> success() {
        return new JsonResponse<>();
    }

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>(true, 0, "成功", data);
    }

    public static <T> JsonResponse<T> failed(Integer code, String message) {
        return new JsonResponse<>(false, code, message);
    }

    public static <T> JsonResponse<T> failed(String message) {
        return new JsonResponse<>(false, 400000, message);
    }

    public static <T> JsonResponse<T> failed(ExceptionEnum exceptionEnum) {
        return new JsonResponse<>(false, exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> JsonResponse<T> validateFailed() {
        return failed(ExceptionEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> JsonResponse<T> validateFailed(String message) {
        return new JsonResponse<>(false, ExceptionEnum.VALIDATE_FAILED.getCode(), message, null);
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
}
