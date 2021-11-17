package com.kabunx.erp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "router")
public class RouterConfig {
    // 开发路由，直接跳过鉴权
    List<String> openApis;

    // 非必要鉴权的路由
    List<String> dispensableApis;
}
