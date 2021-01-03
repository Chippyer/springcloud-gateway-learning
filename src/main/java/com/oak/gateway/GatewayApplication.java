package com.oak.gateway;

import com.oak.gateway.fitler.factory.LoggingGatewayFilterFactory;
import com.oak.gateway.fitler.factory.RateLimiterGatewayFilterFactory;
import com.oak.gateway.limit.resolver.HostAddrKeyResolver;
import com.oak.gateway.limit.resolver.UriKeyResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
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

    /*
        解决全局设置限流拦截器问题
        1. 复制{@link org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter}类重写构造方法，
        具体参考{@link com.oak.gateway.fitler.factory.RedisRateLimiter}实现
        2. 采用如下的注入方式进行住，否则执行时会服务会找不到对应的限流配置导致请求异常
     */
    // @Bean
    // public RateLimiterGatewayFilterFactory rateLimiterGatewayFilterFactory(HostAddrKeyResolver hostAddrKeyResolver,
    //     ReactiveRedisTemplate<String, String> redisTemplate, RedisScript<List<Long>> redisScript, Validator validator) {
    //     final RedisRateLimiter redisRateLimiter = new RedisRateLimiter(redisTemplate, redisScript, validator, 1, 5);
    //     return new RateLimiterGatewayFilterFactory(redisRateLimiter, hostAddrKeyResolver);
    // }

    @Bean
    public RateLimiterGatewayFilterFactory rateLimiterGatewayFilterFactory(RedisRateLimiter redisRateLimiter,
        KeyResolver hostAddrKeyResolver) {
        return new RateLimiterGatewayFilterFactory(redisRateLimiter, hostAddrKeyResolver);
    }

    @Bean
    public LoggingGatewayFilterFactory loggingGatewayFilterFactory() {
        return new LoggingGatewayFilterFactory(null);
    }

}
