package com.oyenavneet.playground.sec07;

import com.oyenavneet.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.UUID;

public class Lec09ExchangeFilterTest extends AbstractWebClient{

    private static final Logger logger = LoggerFactory.getLogger(Lec09ExchangeFilterTest.class);


    private final WebClient webClient = createWebClient(b -> b.filter(tokenGenerator())
            .filter(requestLogger())
    );

    @Test
    public void exchangeFilter()  {
        this.webClient.get()
                .uri("/lec09/product/{id}",1)
                .attribute("enable-logging",true) // in case need to pass some attributes
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }

    // helper method to generate the token for request , as request are immutable we're making a copy and modifying it using ClientRequest.from()
    private ExchangeFilterFunction tokenGenerator(){
        return (request, next) -> {
            var token = UUID.randomUUID().toString().replace("-", "");
            logger.info("token: {}", token);
            var modifiedRequest = ClientRequest.from(request).headers(h->h.setBearerAuth(token)).build();
            return next.exchange(modifiedRequest);
        };
    }


    private ExchangeFilterFunction requestLogger(){
        return (request, next) -> {
            var isEnabled = (Boolean) request.attributes().getOrDefault("enable-logging",false); // we can get attributed using request
            if(isEnabled){
                logger.info("request URL: {} : {}", request.method(),request.url());
            }
            return next.exchange(request);
        };
    }
}
