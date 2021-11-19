package com.kabunx.erp.validator;

import com.kabunx.erp.config.RouterConfig;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    @Resource
    RouterConfig routerConfig;

    public Predicate<ServerHttpRequest> isProtected =
            request -> routerConfig.getOpenApis().stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
