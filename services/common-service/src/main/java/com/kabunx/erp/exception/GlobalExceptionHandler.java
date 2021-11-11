package com.kabunx.erp.exception;

import com.kabunx.erp.domain.JsonResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一的全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public JsonResponse<Object> handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return JsonResponse.failed(e.getErrorCode());
        }
        return JsonResponse.failed(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResponse<Object> handleValidException(MethodArgumentNotValidException e) {
        return JsonResponse.validateFailed(collectErrorMessage(e.getBindingResult()));
    }

    @ExceptionHandler(value = BindException.class)
    public JsonResponse<Object> handleValidException(BindException e) {
        return JsonResponse.validateFailed(collectErrorMessage(e.getBindingResult()));
    }

    /**
     * 错误信息收集
     *
     * @param bindingResult 绑定结果
     * @return 错误信息
     */
    private String collectErrorMessage(BindingResult bindingResult) {
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return message;
    }
}
