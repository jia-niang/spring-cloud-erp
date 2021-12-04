package com.kabunx.erp.config;

import com.kabunx.erp.interceptor.AuthHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebAuthConfig extends WebMvcConfigurationSupport {

    @Bean
    public AuthHeaderInterceptor authHeaderInterceptor() {
        return new AuthHeaderInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHeaderInterceptor());
        super.addInterceptors(registry);
    }
}
