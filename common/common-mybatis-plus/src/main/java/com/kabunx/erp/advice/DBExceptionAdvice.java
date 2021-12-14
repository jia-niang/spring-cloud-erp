package com.kabunx.erp.advice;

import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.exception.PlusException;
import com.kabunx.erp.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 数据库异常处理
 */
@Slf4j
@RestControllerAdvice
@Order(-16)
public class DBExceptionAdvice {

    @ExceptionHandler(value = DuplicateKeyException.class)
    public JsonResponse<Object> handleValidException(DuplicateKeyException e) {
        log.error(e.getMessage());
        return JsonResponse.failed(ExceptionEnum.DB_FAILED);
    }

    @ExceptionHandler(value = DataAccessException.class)
    public JsonResponse<Object> handleValidException(DataAccessException e) {
        log.error(e.getMessage());
        return JsonResponse.failed(ExceptionEnum.DB_FAILED);
    }

    @ExceptionHandler(value = PlusException.class)
    public JsonResponse<Object> handleValidException(PlusException e) {
        if (e.getPlusExceptionEnum() != null) {
            return JsonResponse.failed(
                    e.getPlusExceptionEnum().getCode(),
                    e.getPlusExceptionEnum().getMessage()
            );
        }
        if (e.getExceptionEnum() != null) {
            return JsonResponse.failed(e.getExceptionEnum());
        }
        return JsonResponse.failed(ExceptionEnum.DB_FAILED);
    }

}
