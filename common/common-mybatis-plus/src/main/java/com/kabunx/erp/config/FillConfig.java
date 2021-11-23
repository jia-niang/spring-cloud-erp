package com.kabunx.erp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mybatis.fill")
public class FillConfig {
    String timestamp = "s";

    String createdColumn = "createdAt";

    String updatedColumn = "updatedAt";
}
