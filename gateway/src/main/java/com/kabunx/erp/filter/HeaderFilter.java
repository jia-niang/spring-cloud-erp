package com.kabunx.erp.filter;

import com.kabunx.erp.constant.SecurityConstant;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 优先处理头信息，一定要高于认证过滤器
 */
public class HeaderFilter implements GlobalFilter, Ordered {

    /**
     * 永不信任前端的用户信息，必须由系统解析后添加
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers((httpHeaders) -> {
                    httpHeaders.remove(SecurityConstant.USER_ID_HEADER);
                    httpHeaders.remove(SecurityConstant.USER_TYPE_HEADER);
                })
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
