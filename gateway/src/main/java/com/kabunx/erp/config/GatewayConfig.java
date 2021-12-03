package com.kabunx.erp.config;

import com.kabunx.erp.exception.JsonErrorAttributes;
import com.kabunx.erp.exception.JsonErrorWebExceptionHandler;
import com.kabunx.erp.exception.UnifyWebExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebExceptionHandler;

import java.util.stream.Collectors;

/**
 * 自定义异常信息处理
 * 具体请参考 ErrorWebFluxAutoConfiguration
 */
@Configuration
public class GatewayConfig {

    @Bean
    @Order(-16)
    public WebExceptionHandler unifyWebExceptionHandler() {
        return new UnifyWebExceptionHandler();
    }

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

    @Bean
    public ErrorAttributes errorAttributes() {
        return new JsonErrorAttributes();
    }
}
