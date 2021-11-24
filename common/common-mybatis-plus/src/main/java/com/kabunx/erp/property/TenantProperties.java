package com.kabunx.erp.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "mybatis.tenant")
public class TenantProperties {
    boolean enable = false;

    String id;

    String column;

    List<String> tables;
}
