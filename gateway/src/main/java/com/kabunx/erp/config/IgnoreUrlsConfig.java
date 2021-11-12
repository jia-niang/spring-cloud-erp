package com.kabunx.erp.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "safety.ignore")
@Configuration
public class IgnoreUrlsConfig {
    private List<String> urls;
}
