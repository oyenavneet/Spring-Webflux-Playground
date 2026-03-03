package com.oyenavneet.playground.sec07;

import com.oyenavneet.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec08BearerAuthTest extends AbstractWebClient{

    private static final Logger logger = LoggerFactory.getLogger(Lec08BearerAuthTest.class);

    private final WebClient webClient = createWebClient( b -> b.defaultHeaders( h -> h.setBearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")));

    @Test
    public void bearerAuth()  {
        this.webClient.get()
                .uri("/lec08/product/{id}",1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }


}
