package com.kabunx.erp.config;

import com.kabunx.erp.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RouteLocatorConfig {
    @Resource
    KeyResolver ipKeyResolver;

    @Resource
    RateLimiter redisRateLimiter;

    @Resource
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://erp-auth-service")
                )
                .route(r -> r.path("/users/**")
                        .filters(f -> f.filter(filter)
                                .requestRateLimiter(limiter -> {
                                    limiter.setKeyResolver(ipKeyResolver);
                                    limiter.setRateLimiter(redisRateLimiter);
                                })
                        )
                        .uri("lb://erp-user-service")
                )
                .build();
    }
}
