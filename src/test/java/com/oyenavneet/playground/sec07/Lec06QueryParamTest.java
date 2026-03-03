package com.oyenavneet.playground.sec07;

import com.oyenavneet.playground.sec07.dto.CalculatorResponse;
import com.oyenavneet.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec06QueryParamTest extends AbstractWebClient {

    private static final Logger logger = LoggerFactory.getLogger(Lec06QueryParamTest.class);



    // default header
    private final WebClient webClient = createWebClient();

    @Test
    public void queryParamTest()  {
        var path = "/lec06/calculator";
        var query = "first={first}&second={second}&operation={operation}";
        this.webClient.get()
                .uri(builder -> builder.path(path).query(query).build(10,20, "+")) // using UriBuilder we can input query Params, it works on place bases
                .header("caller-id", "test-service1") //  override header
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }


    @Test
    public void queryParamMapTest()  {
        var path = "/lec06/calculator";
        var query = "first={first}&second={second}&operation={operation}";
        var map = Map.of(
                "first", 10,
                "second", 20,
                "operation", "+"
        );
        this.webClient.get()
                .uri(builder -> builder.path(path).query(query).build(map)) // using UriBuilder we can input query Params, map worked on key based
                .header("caller-id", "test-service1") //  override header
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }

}
