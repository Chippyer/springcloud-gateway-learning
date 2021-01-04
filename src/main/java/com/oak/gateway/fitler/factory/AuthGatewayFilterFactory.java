package com.oak.gateway.fitler.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 权限过滤器
 *
 * @author: chippy
 * @datetime 2021/1/5 1:52
 */
@Slf4j
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {

    private StringRedisTemplate redisTemplate;

    @Override
    public GatewayFilter apply(LoggingGatewayFilterFactory.Config config) {

        return null;
    }

}
