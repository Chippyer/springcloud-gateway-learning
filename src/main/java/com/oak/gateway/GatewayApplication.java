package com.oak.gateway;

import com.oak.gateway.limit.factory.RateLimiterGatewayFilterFactory;
import com.oak.gateway.limit.resolver.HostAddrKeyResolver;
import com.oak.gateway.limit.resolver.UriKeyResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * 网关服务启动类
 *
 * @author: chippy
 * @datetime 2020-12-31 12:49
 */
@SpringBootApplication
@EnableWebFlux
@Slf4j
@EnableEurekaClient
@EnableHystrix
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }

    @Bean
    public UriKeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }

    @Bean
    public RateLimiterGatewayFilterFactory rateLimiterGatewayFilterFactory(RedisRateLimiter redisRateLimiter,
        HostAddrKeyResolver hostAddrKeyResolver) { // 默认按照地址进行限流
        return new RateLimiterGatewayFilterFactory(redisRateLimiter, hostAddrKeyResolver);
    }

}
