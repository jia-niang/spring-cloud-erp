package com.kabunx.erp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "mybatis-tenant")
public class TenantConfig {
    boolean enable = false;

    String id;

    String column;

    List<String> tables;
}
