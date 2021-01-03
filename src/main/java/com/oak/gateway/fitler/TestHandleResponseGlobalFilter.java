//package com.oak.gateway.fitler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * 测试全局处理器处理响应数据
// * 实GlobalFilter接口并注入Spring容器即可在请求时调用此过滤器
// *
// * @author: chippy
// * @datetime 2020-12-31 16:31
// */
//@Component
//@Slf4j
//public class TestHandleResponseGlobalFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        final ServerHttpResponse response = exchange.getResponse();
//        final DataBufferFactory dataBufferFactory = response.bufferFactory();
//        final ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (body instanceof Flux) {
//                    final Flux<? extends DataBuffer> fluxDataBuffer = (Flux<? extends DataBuffer>)body;
//                    return super.writeWith(fluxDataBuffer.map((dataBuffer -> {
//                        final byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                        dataBuffer.read(bytes);
//                        // 释放掉内存
//                        DataBufferUtils.release(dataBuffer);
//                        final String response = new String(bytes, StandardCharsets.UTF_8);
//                        log.debug("response: " + response);
//                        return dataBufferFactory.wrap(bytes);
//                    })));
//                }
//                return super.writeWith(body);
//            }
//        };
//        return chain.filter(exchange.mutate().response(decoratedResponse).build());
//    }
//
//    @Override
//    public int getOrder() {
//        // -1是响应写过滤器，必须在此之前调用
//        return -2;
//    }
//}
