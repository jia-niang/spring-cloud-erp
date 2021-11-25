package com.kabunx.erp.filter;

import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.entity.User;
import com.kabunx.erp.service.AuthenticationService;
import com.kabunx.erp.validator.RouterValidator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class AuthenticationFilter implements GatewayFilter, Ordered {
    // custom route validator
    @Resource
    RouterValidator routerValidator;

    @Resource
    AuthenticationService authenticationService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        boolean authorized = false;
        if (!this.isAuthorizationMissing(request)) {
            Optional<String> token = getAuthorizationToken(request);
            if (token.isPresent()) {
                Optional<User> auth = authenticationService.parseToken(token.get());
                if (auth.isPresent()) {
                    authorized = true;
                    fillRequestHeaders(exchange, auth.get());
                }
            }
        }
        // 受保护的接口且没有认证
        if (routerValidator.isProtected.test(request) && !authorized) {
            return onAuthenticationError(exchange);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onAuthenticationError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer dataBuffer = response.bufferFactory()
                .wrap(SecurityConstant.AUTHORIZATION_ERROR.getBytes(StandardCharsets.UTF_8));
        response.writeWith(Mono.just(dataBuffer));
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private Optional<String> getAuthorizationToken(ServerHttpRequest request) {
        String token = this.getAuthorizationHeader(request);
        if (token == null) {
            return Optional.empty();
        }
        token = token.replace(SecurityConstant.AUTHORIZATION_TOKEN_PREFIX, Strings.EMPTY).trim();
        return token.isEmpty() ? Optional.empty() : Optional.of(token);
    }

    private String getAuthorizationHeader(ServerHttpRequest request) {
        return request.getHeaders().getFirst(SecurityConstant.AUTHORIZATION_HEADER);
    }

    private boolean isAuthorizationMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(SecurityConstant.AUTHORIZATION_HEADER);
    }

    // 将解析的用户填充到header中并返回是否验证通过
    private void fillRequestHeaders(ServerWebExchange exchange, User user) {
        exchange.getRequest()
                .mutate()
                .header(SecurityConstant.USER_ID_HEADER, user.getId())
                .header(SecurityConstant.USER_TYPE_HEADER, user.getType())
                .build();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
