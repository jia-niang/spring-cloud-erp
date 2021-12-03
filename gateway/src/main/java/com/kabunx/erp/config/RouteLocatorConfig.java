package com.kabunx.erp.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO 配置中心替换
 */
@Configuration
public class RouteLocatorConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        return routes
                .route(r -> r.path("/auth/**")
                        .uri("lb://erp-auth-service")
                )
                .route(r -> r.path("/user/**")
                        .uri("lb://erp-user-service")
                )
                .build();
    }
}
