package com.edu.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
public class MyPostFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyPostFilter.class);

    @Bean
    public GlobalFilter posGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                List<String> startTimeValues = requestHeaders.get("startTime");
                String startTime = startTimeValues.stream().findFirst().get();
                LOGGER.info("$$$$ startTime: {}", startTime);

                long duration = ChronoUnit.MILLIS.between(Instant.parse(startTime), Instant.now());
                LOGGER.info("$$$$ duration of the request: {}", duration);

            }));
        };
    }
}
