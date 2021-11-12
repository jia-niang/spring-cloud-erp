package com.kabunx.erp.exception;

import com.kabunx.erp.domain.JsonResponseBody;
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

    @ExceptionHandler(value = ErpException.class)
    public JsonResponseBody<Object> handle(ErpException e) {
        if (e.getErrorCode() != null) {
            return JsonResponseBody.failed(e.getErrorCode());
        }
        return JsonResponseBody.failed(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResponseBody<Object> handleValidException(MethodArgumentNotValidException e) {
        return JsonResponseBody.validateFailed(collectErrorMessage(e.getBindingResult()));
    }

    @ExceptionHandler(value = BindException.class)
    public JsonResponseBody<Object> handleValidException(BindException e) {
        return JsonResponseBody.validateFailed(collectErrorMessage(e.getBindingResult()));
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
