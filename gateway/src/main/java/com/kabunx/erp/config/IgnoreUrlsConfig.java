package com.kabunx.erp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "safety.ignore")
@Configuration
public class IgnoreUrlsConfig {
    private List<String> urls;
}
