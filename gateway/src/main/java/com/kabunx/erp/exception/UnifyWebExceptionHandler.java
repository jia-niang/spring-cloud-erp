package com.kabunx.erp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 统一返回网关异常和状态无关
 */
public class UnifyWebExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof GatewayException) {
            return Mono.error(ex);
        }
        ExceptionEnum exceptionEnum = ExceptionEnum.GATEWAY_FAILED;
        // 404 特殊处理
        if (ex instanceof ResponseStatusException) {
            HttpStatus httpStatus = ((ResponseStatusException) ex).getStatus();
            if (httpStatus == HttpStatus.NOT_FOUND) {
                exceptionEnum = ExceptionEnum.GATEWAY_NOT_FOUND;
            }
        }
        return Mono.error(new GatewayException(exceptionEnum));
    }
}
