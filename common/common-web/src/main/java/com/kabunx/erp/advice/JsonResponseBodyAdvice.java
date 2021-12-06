package com.kabunx.erp.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabunx.erp.constraints.IgnoreJsonResponseBodyAdvice;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.exception.ExceptionEnum;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
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
    public Object beforeBodyWrite(
            @Nullable Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        response.setStatusCode(HttpStatus.OK);
        if (body == null) {
            return JsonResponse.success();
        } else if (body instanceof String) {
            // 对String类型需要特殊处理，否则会报错
            try {
                return toJsonString(JsonResponse.success(body));
            } catch (JsonProcessingException e) {
                return JsonResponse.failed(ExceptionEnum.FAILED);
            }
        } else if (body instanceof JsonResponse) {
            return body;
        } else {
            return JsonResponse.success(body);
        }
    }

    private String toJsonString(JsonResponse<Object> body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(body);
    }
}
