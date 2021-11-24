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

    public Predicate<ServerHttpRequest> isProtected =
            request -> routerProperties.getOpenApis().stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
