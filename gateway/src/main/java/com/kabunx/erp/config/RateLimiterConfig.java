package com.kabunx.erp.config;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RateLimiterConfig {
    // 创建一个限流维度，可以支持多维度限流策略
    // ip+path or user+path

    // 限流器
    @Bean
    @Primary
    public RedisRateLimiter redisRateLimiter() {
        // 每个令牌桶每秒速率 + 令牌桶数
        return new RedisRateLimiter(10, 5);
    }
}
