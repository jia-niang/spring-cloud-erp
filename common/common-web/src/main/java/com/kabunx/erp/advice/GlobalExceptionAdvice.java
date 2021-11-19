package com.kabunx.erp.advice;

import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.exception.BizException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 统一的全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = BizException.class)
    public JsonResponseBody<Object> handle(BizException e) {
        if (e.getErrorCode() != null) {
            return JsonResponseBody.failed(e.getErrorCode());
        }
        return JsonResponseBody.failed(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResponseBody<Object> handleValidException(MethodArgumentNotValidException e) {
        return JsonResponseBody.validateFailed(collectFieldErrors(e.getBindingResult()));
    }

    @ExceptionHandler(value = BindException.class)
    public JsonResponseBody<Object> handleValidException(BindException e) {
        return JsonResponseBody.validateFailed(collectFieldErrors(e.getBindingResult()));
    }

    /**
     * 错误信息收集
     *
     * @param bindingResult 绑定结果
     * @return 错误信息
     */
    private HashMap<String, ArrayList<String>> collectFieldErrors(BindingResult bindingResult) {
        HashMap<String, ArrayList<String>> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                ArrayList<String> fErrors = errors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>());
                fErrors.add(fieldError.getDefaultMessage());
            }
        }
        return errors;
    }
}
