package com.kabunx.erp.filter;

import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.util.JwtUtil;
import com.kabunx.erp.validator.RouterValidator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
    // custom route validator
    @Resource
    RouterValidator routerValidator;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //
        if (routerValidator.isProtected.test(request)) {
            // 受保护的接口
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, "Authorization header is missing in request");
            }
            final String token = this.getAuthHeader(request);
            if (jwtUtil.isInvalid(token)) {
                return this.onError(exchange, "Authorization header is invalid");
            }
            this.populateRequestWithHeaders(exchange, token);
        } else if (routerValidator.isDispensable.test(request)) {
            // 对鉴权可有可无的接口
            if (!this.isAuthMissing(request)) {
                final String token = this.getAuthHeader(request);
                if (!jwtUtil.isInvalid(token)) {
                    this.populateRequestWithHeaders(exchange, token);
                }
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getFirst(SecurityConstant.JWT_TOKEN_HEADER);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(SecurityConstant.JWT_TOKEN_HEADER);
    }

    // 将解析的用户填充到header中
    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header(SecurityConstant.USER_ID_HEADER, String.valueOf(claims.get("id")))
                .build();
    }
}
