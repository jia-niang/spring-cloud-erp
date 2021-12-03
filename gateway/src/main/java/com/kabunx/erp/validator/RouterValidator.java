package com.kabunx.erp.validator;

import com.kabunx.erp.property.RouterProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    @Resource
    RouterProperties routers;

    public Predicate<ServerHttpRequest> isFree =
            request -> routers.getWhitelist() != null && anyMatch(request);

    public boolean isProtectedRequest(ServerHttpRequest request) {
        return routers.getOpens() == null || noneMatch(request);
    }

    private boolean anyMatch(ServerHttpRequest request) {
        return routers.getWhitelist().stream().anyMatch(uri -> request.getURI().getPath().contains(uri));
    }

    private boolean noneMatch(ServerHttpRequest request) {
        return routers.getOpens().stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}