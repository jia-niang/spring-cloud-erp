package com.kabunx.erp.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.entity.Member;
import com.kabunx.erp.entity.User;
import com.kabunx.erp.exception.ExceptionEnum;
import com.kabunx.erp.exception.GatewayException;
import com.kabunx.erp.service.AuthenticationService;
import com.kabunx.erp.util.HashUtils;
import com.kabunx.erp.validator.RouterValidator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * 认证过滤器
 */
public class AuthenticationFilter implements GlobalFilter, Ordered {
    // custom route validator
    private final RouterValidator routerValidator;

    private final AuthenticationService authenticationService;

    public AuthenticationFilter(RouterValidator routerValidator, AuthenticationService authenticationService) {
        this.routerValidator = routerValidator;
        this.authenticationService = authenticationService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routerValidator.isFree.test(request)) {
            return chain.filter(exchange);
        }
        Optional<String> token = getAuthorizationToken(request);
        if (token.isPresent()) {
            // 自定义或jwt
            if (isCustomToken(token.get())) {
                // 异步验证
                Mono<String> response = authenticationService.checkByCustomToken(token.get());
                if (response == null) {
                    return filterByAuth(exchange, chain, false);
                } else {
                    return response.flatMap(s -> {
                        JsonResponse<Member> jsonResponse = map2JsonResponse(s);
                        if (jsonResponse.unavailable()) {
                            return Mono.error(
                                    new GatewayException(ExceptionEnum.UNAUTHORIZED)
                            );
                        }
                        return chain.filter(exchange);
                    });
                }
            } else {
                Optional<User> user = authenticationService.parseJwt2User(token.get());
                user.ifPresent(value -> fillRequestHeaders(exchange, value));
                return filterByAuth(exchange, chain, true);
            }
        } else {
            return filterByAuth(exchange, chain, false);
        }
    }

    private Mono<Void> filterByAuth(ServerWebExchange exchange, GatewayFilterChain chain, Boolean authenticated) {
        if (!authenticated && routerValidator.isProtectedRequest(exchange.getRequest())) {
            throw new GatewayException(ExceptionEnum.UNAUTHORIZED);
        }
        return chain.filter(exchange);
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

    /**
     * @param token header token
     * @return 是否为自定义token
     */
    private boolean isCustomToken(String token) {
        return token.contains(SecurityConstant.AUTHORIZATION_TOKEN_SPLIT);
    }

    private boolean validateCustomToken(String plainToken, String token) {
        try {
            return token.equals(HashUtils.encryptSha256(plainToken));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    // 将解析的用户填充到Header中
    private void fillRequestHeaders(ServerWebExchange exchange, User user) {
        exchange.getRequest()
                .mutate()
                .header(SecurityConstant.USER_ID_HEADER, user.getId())
                .header(SecurityConstant.USER_TYPE_HEADER, user.getType())
                .build();
    }

    public JsonResponse<Member> map2JsonResponse(String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(content, new TypeReference<JsonResponse<Member>>() {
            });
        } catch (JsonProcessingException e) {
            return JsonResponse.error();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
