package com.oyenavneet.playground.sec07;

import com.oyenavneet.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Lec03Post extends AbstractWebClient {

    private final WebClient webClient = createWebClient();

    @Test
    public void postBodyValue()  {
        var product = new Product(null, "bottle", 100);
        this.webClient.post()
                .uri("/lec03/product")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class) // response object
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }


    @Test
    public void postBody()  {
        var mono = Mono.fromSupplier( () -> new Product(null, "mouse", 100))
                .delayElement(Duration.ofSeconds(1));

        this.webClient.post()
                .uri("/lec03/product")
                .body(mono,  Product.class)
                .retrieve()
                .bodyToMono(Product.class) // response object
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }
}
