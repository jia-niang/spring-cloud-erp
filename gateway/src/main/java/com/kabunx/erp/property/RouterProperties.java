package com.kabunx.erp.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "erp.gateway.router")
public class RouterProperties {
    // 白名单，直接跳过认证
    List<String> whitePaths;

    // 开放路由，会先认证，但认证失败也不会中断请求
    List<String> openPaths;
}
