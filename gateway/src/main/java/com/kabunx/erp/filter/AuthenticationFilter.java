package com.kabunx.erp.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kabunx.erp.constant.GlobalConstant;
import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.entity.Member;
import com.kabunx.erp.entity.User;
import com.kabunx.erp.exception.ExceptionEnum;
import com.kabunx.erp.exception.GatewayException;
import com.kabunx.erp.service.AuthenticationService;
import com.kabunx.erp.validator.RouterValidator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 认证过滤器
 */
public class AuthenticationFilter implements GlobalFilter, Ordered {
    private static final JsonResponseTypeReference RESPONSE_TYPE = new JsonResponseTypeReference();

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
        AtomicBoolean authenticated = new AtomicBoolean(false);
        if (token.isPresent()) {
            // 自定义或jwt
            if (isCustomToken(token.get())) {
                // 解析并异步验证
                String[] elements = parseCustomToken(token.get());
                if (elements != null) {
                    Mono<String> response = authenticationService.findUserById(Long.parseLong(elements[0]));
                    if (response != null) {
                        return response.flatMap(s -> {
                            JsonResponse<Member> jsonResponse = Hydrate.map2JsonResponse(s, RESPONSE_TYPE);
                            if (jsonResponse.available()) {
                                Optional<User> user = authenticationService.validateToken2User(jsonResponse.getData(), elements[1]);
                                user.ifPresent(value -> {
                                    authenticated.set(true);
                                    fillRequestHeaders(exchange, value);
                                });
                            }
                            if (!authenticated.get() && routerValidator.isProtectedRequest(request)) {
                                return Mono.error(new GatewayException(ExceptionEnum.UNAUTHORIZED));
                            }
                            return chain.filter(exchange);
                        });
                    }
                }
            } else {
                Optional<User> user = authenticationService.parseJwt2User(token.get());
                user.ifPresent(value -> {
                    authenticated.set(true);
                    fillRequestHeaders(exchange, value);
                });
            }
        }
        if (!authenticated.get() && routerValidator.isProtectedRequest(request)) {
            throw new GatewayException(ExceptionEnum.UNAUTHORIZED);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
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

    private String[] parseCustomToken(String token) {
        String[] elements = token.split(GlobalConstant.BASE_STRING_REGEX, 2);
        if (elements.length != 2) {
            return null;
        }
        return elements;
    }

    // 将解析的用户填充到Header中
    private void fillRequestHeaders(ServerWebExchange exchange, User user) {
        exchange.getRequest()
                .mutate()
                .header(SecurityConstant.USER_ID_HEADER, user.getId())
                .header(SecurityConstant.USER_TYPE_HEADER, user.getType())
                .build();
    }

    private static class JsonResponseTypeReference extends TypeReference<JsonResponse<Member>> {

    }
}
