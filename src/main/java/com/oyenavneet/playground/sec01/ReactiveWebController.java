package com.oyenavneet.playground.sec01;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


/*
    Simple Reactive Rest Controller
    - calling external service api which take 10 sec to give complete response - but when the product get arriving in response/log
    - Same as tradition it will take 10 sec to give response, but in reactive when we cancel thr request it stop immoderately
    - as in reactive programing we have publishe subscribe concept, and subscribe can cancel the subscribe any time and producer stop emitting data
    - same concept apply here
    - To test : curl -N http://localhost:8080/reactive/products
 */
@RestController
@RequestMapping("reactive")
public class ReactiveWebController {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveWebController.class);
    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:7070").build();

    @GetMapping("products")
    public Flux<Product> getProducts() {
        return webClient
                .get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(product -> logger.info("received product {}", product));
    }


}
