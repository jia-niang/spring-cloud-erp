package com.kabunx.erp.advice;

import com.kabunx.erp.domain.JsonResponseBody;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Oauth2ExceptionAdvice {

    @ExceptionHandler(value = OAuth2Exception.class)
    public JsonResponseBody<Object> handleOauth2Exception(OAuth2Exception e) {
        return JsonResponseBody.failed(e.getMessage());
    }
}
