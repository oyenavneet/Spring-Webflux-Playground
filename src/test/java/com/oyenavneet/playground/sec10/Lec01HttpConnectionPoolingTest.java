package com.oyenavneet.playground.sec10;

import com.oyenavneet.playground.sec10.dto.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.test.StepVerifier;

/*
    - WebClient - by default - will manage 500 connection to a remote service
    - It is configurable
    - When we configure web client wee need to configure all other properties which by default enabled by WebClient.builder()
    - There is a limit ot poolSize which is deffer machine to machine

 */

public class Lec01HttpConnectionPoolingTest extends AbstractWebClient {

    private final WebClient webClient = createWebClient(b -> {
        var poolSize = 10000;
        var provider = ConnectionProvider.builder("sri")
                .lifo()
                .maxConnections(poolSize)
                .pendingAcquireMaxCount(poolSize * 5) // it will queue extra request which is more than pool size
                .build();
        var httpClient = HttpClient.create(provider)
                .compress(true)
                .keepAlive(true);

        b.clientConnector(new ReactorClientHttpConnector(httpClient));
    });

    @Test
    public void concurrentRequest() {
        var max = 10000;
        Flux.range(1, max)
                .flatMap(this::getProduct, max) // second parameter is to increase the concurrency of flatmap
                .collectList()
                .as(StepVerifier::create)
                .assertNext(products -> Assertions.assertEquals(max, products.size()))
                .expectComplete()
                .verify();

//        Thread.sleep(Duration.ofMinutes(1));
    }


    private Mono<Product> getProduct(int id) {
        return this.webClient.get()
                .uri("/product/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);

    }

}
