package com.kabunx.erp.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 自定义异常信息处理
 */
public class JsonErrorAttributes implements ErrorAttributes {

    private static final String ERROR_INTERNAL_ATTRIBUTE = JsonErrorAttributes.class.getName() + ".ERROR";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        Integer code = ExceptionEnum.GATEWAY_FAILED.getCode();
        String message = ExceptionEnum.GATEWAY_FAILED.getMessage();
        Throwable error = getError(request);
        if (error instanceof GatewayException) {
            ExceptionEnum exceptionEnum = ((GatewayException) error).getExceptionEnum();
            if (exceptionEnum != null) {
                code = exceptionEnum.getCode();
                message = exceptionEnum.getMessage();
            }
        }
        // 统一返回
        errorAttributes.put("success", false);
        errorAttributes.put("code", code);
        errorAttributes.put("message", message);
        errorAttributes.put("data", null);
        return errorAttributes;
    }

    @Override
    public Throwable getError(ServerRequest request) {
        Optional<Object> error = request.attribute(ERROR_INTERNAL_ATTRIBUTE);
        error.ifPresent((value) -> request.attributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, value));
        return (Throwable) error.orElseThrow(() -> new GatewayException(ExceptionEnum.GATEWAY_FAILED));
    }

    @Override
    public void storeErrorInformation(Throwable error, ServerWebExchange exchange) {
        exchange.getAttributes().putIfAbsent(ERROR_INTERNAL_ATTRIBUTE, error);
    }
}
