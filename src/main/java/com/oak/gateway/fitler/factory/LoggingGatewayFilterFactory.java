package com.oak.gateway.fitler.factory;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 日志过滤器
 *
 * @author: chippy
 * @datetime 2021/1/4 0:19
 */
@Slf4j
public class LoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {

    public static final String IGNORE_URLS = "ignoreUrls";
    private String ignoreUrls;

    public LoggingGatewayFilterFactory(String ignoreUrls) {
        super(Config.class);
        this.ignoreUrls = ignoreUrls;
    }

    @Override
    public GatewayFilter apply(LoggingGatewayFilterFactory.Config config) {
        final String ignoreUrlsStr = config.getIgnoreUrls();
        List<String> ignoreUrls =
            Objects.isNull(ignoreUrlsStr) ? Collections.emptyList() : Arrays.asList(ignoreUrlsStr.split(","));
        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            if (Objects.isNull(route)) {  // 为空直接放行
                return chain.filter(exchange);
            }

            final ServerHttpRequest request = exchange.getRequest();
            final String path = request.getURI().getPath();
            final MultiValueMap<String, String> queryParams = request.getQueryParams();
            if (ignoreUrls.contains(request.getURI().getPath())) { // 忽略URI不打印
                return chain.filter(exchange);
            }
            log.debug("请求地址[" + path + "], 请求参数[" + JSONUtil.toJsonStr(queryParams) + "]");
            return chain.filter(exchange);
        };
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Config {
        private String ignoreUrls;
    }

}
