package com.oyenavneet.playground.sec05.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;


@Service
@Order(1)
public class AuthenticationWebFilter implements WebFilter {

    @Autowired
    private FilterErrorHandler filterErrorHandler;

    public static final Map<String, Category> TOKEN_CATEGORY_MAP = Map.of(
            "secret123", Category.STANDARD,
            "secret456", Category.PRIME
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var token = exchange.getRequest().getHeaders().getFirst("auth-token");
        if(Objects.nonNull(token) && TOKEN_CATEGORY_MAP.containsKey(token)) {
            exchange.getAttributes().put("category", TOKEN_CATEGORY_MAP.get(token)); // getAttributes is a map which will be accessible through the request
            return chain.filter(exchange);
        }
//        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
        return filterErrorHandler.sendProblemDetail(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized");
    }
}
