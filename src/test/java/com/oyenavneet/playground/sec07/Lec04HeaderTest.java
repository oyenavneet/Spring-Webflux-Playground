package com.oyenavneet.playground.sec07;

import com.oyenavneet.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;


public class Lec04HeaderTest extends AbstractWebClient{

    // default header
    private final WebClient webClient = createWebClient( b -> b.defaultHeader("caller-id", "test-service"));

    @Test
    public void defaultHeaderTest()  {
        this.webClient.get()
                .uri("/lec04/product/{id}",1)
                .header("caller-id", "test-service1") //  override header
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }


    @Test
    public void defaultHeaderWithMap()  {

        var headerMap = Map.of(
                "caller-id", "test-service-1",
                "test-key",  "test-value"
        );
        this.webClient.get()
                .uri("/lec04/product/{id}",1)
                .headers(h-> h.setAll(headerMap)) //  override header having multiple value
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }

}
