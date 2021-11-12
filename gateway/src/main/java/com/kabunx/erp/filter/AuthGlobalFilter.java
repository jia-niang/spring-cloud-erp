package com.kabunx.erp.filter;

import cn.hutool.core.util.StrUtil;
import com.kabunx.erp.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 将登录用户的JWT转化成用户信息的全局过滤器
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(SecurityConstant.JWT_TOKEN_HEADER);
        // 不是正确的的JWT不做解析处理
        if (StrUtil.isEmpty(token) || !StrUtil.startWithIgnoreCase(token, SecurityConstant.JWT_TOKEN_PREFIX)) {
            return chain.filter(exchange);
        }
        // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在则拦截访问
        String realToken = token.replace(SecurityConstant.JWT_TOKEN_PREFIX, Strings.EMPTY);
        String payload = "xx";
        log.info("AuthGlobalFilter.filter() user:{}", payload);
        // 存在token且不是黑名单，request写入JWT的载体信息
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header(SecurityConstant.USER_TOKEN_HEADER, encodePayload(payload))
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private String encodePayload(String payload) {
        try {
            return URLEncoder.encode(payload, "UTF-8");
        } catch (RuntimeException | IOException e) {
            return Strings.EMPTY;
        }
    }
}
