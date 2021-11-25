package com.kabunx.erp.config;

import com.kabunx.erp.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, AuthenticationFilter authFilter) {
        return builder.routes()
                .route(r -> r.path("/auth/**")
//                        .filters(f -> f.filter(authenticationFilter))
                                .uri("lb://erp-auth-service")
                )
                .route(r -> r.path("/user/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://erp-user-service")
                )
                .build();
    }
}
