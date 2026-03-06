package com.oyenavneet.playground.sec10;

import com.oyenavneet.playground.sec10.dto.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.test.StepVerifier;

/*
    - HTTP2 Can handle even 40K concurrentRequest where HTTP1 is failing in event 15K concurrentRequest
    - Http2 will be beneficial in case where you need many concurrent request. like more than 10K
    - When using HTTP2 ensure that you load balancer support HTTP2
 */

public class Lec02Http2Test extends AbstractWebClient {

    private final WebClient webClient = createWebClient(b -> {
        var poolSize = 1;
        var provider = ConnectionProvider.builder("sri")
                .lifo()
                .maxConnections(poolSize)
                .build();
        var httpClient = HttpClient.create(provider)
                .protocol(HttpProtocol.H2C) // if you don't have ssl/tls enable, if enabled then use H2
                .compress(true)
                .keepAlive(true);

        b.clientConnector(new ReactorClientHttpConnector(httpClient));
    });

    @Test
    public void concurrentRequest() {
        var max = 40000;
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
