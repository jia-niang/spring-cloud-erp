package com.kabunx.erp.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "erp.secret")
public class SecretProperties {
    // 使用AES-128-CBC加密模式，key需要为16位、32位
    String smsCodeKey = "1234567890123456";
    String loginAccountKey = "1234567890123456";
}
