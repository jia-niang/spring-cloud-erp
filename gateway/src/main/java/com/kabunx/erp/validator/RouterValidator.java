package com.kabunx.erp.validator;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {
    // 开发路由，直接跳过鉴权
    public static final List<String> openApis = Arrays.asList(
            "/auth/register",
            "/auth/login"
    );
    // 非必要鉴权的路由
    public static final List<String> dispensableApis = Arrays.asList(
            "/products",
            "/inventories"
    );

    public Predicate<ServerHttpRequest> isProtected =
            request -> openApis.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isDispensable =
            request -> dispensableApis.stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

}
