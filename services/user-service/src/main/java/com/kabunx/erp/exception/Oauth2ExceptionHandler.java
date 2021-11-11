package com.kabunx.erp.exception;

import com.kabunx.erp.domain.JsonResponse;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Oauth2ExceptionHandler {

    @ExceptionHandler(value = OAuth2Exception.class)
    public JsonResponse<Object> handleOauth2Exception(OAuth2Exception e) {
        return JsonResponse.failed(e.getMessage());
    }
}
