package com.kabunx.erp.validator;

import com.kabunx.erp.property.RouterProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    @Resource
    RouterProperties routerProperties;

    public Predicate<ServerHttpRequest> isFree =
            request -> routerProperties.getOpenPaths() != null
                    && routerProperties.getWhitePaths().stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public boolean isProtectedRequest(ServerHttpRequest request) {
        return routerProperties.getOpenPaths() == null
                || routerProperties.getOpenPaths().stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}
