package com.kabunx.erp.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabunx.erp.annotation.IgnoreJsonResponseBodyAdvice;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.exception.ExceptionEnum;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应拦截器
 */
@RestControllerAdvice
public class JsonResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasParameterAnnotation(IgnoreJsonResponseBodyAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return JsonResponseBody.success();
        } else if (body instanceof String) {
            try {
                return toJsonString(JsonResponseBody.success(body));
            } catch (JsonProcessingException e) {
                return JsonResponseBody.failed(ExceptionEnum.FAILED);
            }
        } else if (body instanceof JsonResponseBody) {
            return body;
        } else {
            return JsonResponseBody.success(body);
        }
    }

    private String toJsonString(JsonResponseBody<Object> body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(body);
    }
}
