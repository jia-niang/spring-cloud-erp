package com.kabunx.erp.validator;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {
    // 直接跳过鉴权的路由
    public static final List<String> openApiEndpoints = Arrays.asList(
            "/auth/register",
            "/auth/login"
    );

    public static final List<String> flexibleApiEndpoints = Arrays.asList(
            "/jobs",
            ""
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
