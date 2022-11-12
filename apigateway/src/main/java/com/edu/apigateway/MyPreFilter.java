package com.edu.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Order(1)
@Component
public class MyPreFilter implements GlobalFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyPreFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.info("******** Inside Pre-Filter");
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Instant now = Instant.now();
        exchange
                .mutate()
                .request(
                        exchange.getRequest().mutate().header("startTime", now.toString())
                                .build()
                ).build();
        return chain.filter(exchange);
    }
}
