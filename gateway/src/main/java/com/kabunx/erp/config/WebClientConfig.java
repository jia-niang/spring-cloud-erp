package com.kabunx.erp.config;

import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;

/**
 * 全局负载均衡配置
 */
@Configuration
public class WebClientConfig {

    @Resource
    LoadBalancedExchangeFilterFunction lbFilter;

    @Bean
    public WebClient.Builder builder() {
        return WebClient.builder().filter(lbFilter);
    }
}
