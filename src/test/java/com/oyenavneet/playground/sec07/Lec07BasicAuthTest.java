package com.oyenavneet.playground.sec07;

import com.oyenavneet.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Lec07BasicAuthTest extends AbstractWebClient{

    private static final Logger logger = LoggerFactory.getLogger(Lec07BasicAuthTest.class);

    private final WebClient webClient = createWebClient( b -> b.defaultHeaders( h -> h.setBasicAuth("java", "secret")));

    @Test
    public void basicAuth()  {
        this.webClient.get()
                .uri("/lec07/product/{id}",1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }


}
