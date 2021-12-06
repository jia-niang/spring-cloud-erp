package com.kabunx.erp.config;

import com.kabunx.erp.exception.JsonErrorAttributes;
import com.kabunx.erp.exception.JsonErrorWebExceptionHandler;
import com.kabunx.erp.exception.UnifyWebExceptionHandler;
import com.kabunx.erp.filter.AuthenticationFilter;
import com.kabunx.erp.filter.HeaderFilter;
import com.kabunx.erp.service.AuthenticationService;
import com.kabunx.erp.validator.RouterValidator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.config.conditional.ConditionalOnEnabledGlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebExceptionHandler;

import java.util.stream.Collectors;

/**
 * 过滤器&&自定义异常信息处理
 * 过滤器请参考
 * 异常请参考 ErrorWebFluxAutoConfiguration
 */
@Configuration
public class GatewayConfig {

    /**
     * 全局请求头过滤器
     */
    @Bean
    @ConditionalOnEnabledGlobalFilter
    public HeaderFilter headerFilter() {
        return new HeaderFilter();
    }

    /**
     * 全局认证过滤器
     */
    @Bean
    @ConditionalOnEnabledGlobalFilter
    public AuthenticationFilter authenticationFilter(
            RouterValidator routerValidator,
            AuthenticationService authenticationService
    ) {
        return new AuthenticationFilter(routerValidator, authenticationService);
    }

    /**
     * 优先级更高的异常处理，主要是统一404,503的响应
     * 官方的绑定了对状态的处理优先级为"0"
     */
    @Bean
    @Order(-16)
    public WebExceptionHandler unifyWebExceptionHandler() {
        return new UnifyWebExceptionHandler();
    }

    /**
     * 自定义异常处理
     */
    @Bean
    public ErrorWebExceptionHandler errorWebExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties.Resources resources,
            WebProperties webProperties,
            ObjectProvider<ViewResolver> viewResolvers,
            ServerCodecConfigurer serverCodecConfigurer,
            ApplicationContext applicationContext,
            ServerProperties serverProperties
    ) {
        JsonErrorWebExceptionHandler exceptionHandler = new JsonErrorWebExceptionHandler(
                errorAttributes,
                resources.hasBeenCustomized() ? resources : webProperties.getResources(),
                applicationContext,
                serverProperties.getError()
        );
        exceptionHandler.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
        exceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }

    /**
     * 自定义异常数据格式
     */
    @Bean
    public ErrorAttributes errorAttributes() {
        return new JsonErrorAttributes();
    }
}
