package com.oak.gateway.fitler;

import cn.hutool.json.JSONUtil;
import com.oak.gateway.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 权限全局过滤器
 *
 * @author: chippy
 * @datetime 2020-12-31 17:25
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final HttpHeaders headers = request.getHeaders();
        headers.get("at");
        final String token = exchange.getRequest().getHeaders().getFirst("token");
        if (Objects.equals(token, "chippy")) {
            return chain.filter(exchange);
        }
        final ServerHttpResponse response = exchange.getResponse();
        final DataBufferFactory dataBufferFactory = response.bufferFactory();
        return response.writeWith(
            Mono.just(dataBufferFactory.wrap(JSONUtil.toJsonPrettyStr(Result.fail(101, "权限不足")).getBytes())));
    }

}
