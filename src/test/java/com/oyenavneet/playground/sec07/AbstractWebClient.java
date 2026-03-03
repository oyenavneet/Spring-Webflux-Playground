package com.oyenavneet.playground.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

public class AbstractWebClient {

    private static final Logger logger = LoggerFactory.getLogger(AbstractWebClient.class);

    protected <T> Consumer<T> print(){
        return item -> logger.info("received: {}",item);
    }

    protected WebClient createWebClient() {
        return createWebClient( b -> {});

    }

    protected WebClient createWebClient(Consumer<WebClient.Builder> consumer) {
        var builder = WebClient.builder()
                .baseUrl("http://localhost:7070/demo02");
        consumer.accept(builder);
        return builder.build();

    }


}
