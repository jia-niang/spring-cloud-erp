package com.kabunx.erp.filter;

import com.kabunx.erp.config.IgnoreUrlsConfig;
import com.kabunx.erp.constant.SecurityConstant;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * 白名单路径访问时需要移除JWT请求头
 */
public class IgnoreJwtUrlsFilter implements WebFilter {
    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径移除JWT请求头
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                ServerHttpRequest request = exchange.getRequest()
                        .mutate()
                        .header(SecurityConstant.JWT_TOKEN_HEADER, Strings.EMPTY)
                        .build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}
