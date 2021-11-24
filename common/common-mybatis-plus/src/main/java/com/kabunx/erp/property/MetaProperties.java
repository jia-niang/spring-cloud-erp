package com.kabunx.erp.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mybatis.fill")
public class MetaProperties {
    String timestamp = "s";

    String createdColumn = "createdAt";

    String updatedColumn = "updatedAt";
}
