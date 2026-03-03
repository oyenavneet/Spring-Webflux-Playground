package com.oyenavneet.playground.sec07;

import com.oyenavneet.playground.sec07.dto.CalculatorResponse;
import com.oyenavneet.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec05ErrorResponseTest extends AbstractWebClient {

    private static final Logger logger = LoggerFactory.getLogger(Lec05ErrorResponseTest.class);

    // default header
    private final WebClient webClient = createWebClient();

    @Test
    public void handlingError()  {
        this.webClient.get()
                .uri("/lec05/calculator/{a}/{b}",10,20)
                .header("operation","/")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
//                .onErrorReturn(new CalculatorResponse(0,0, null, 0.0)) // in case of error default error response
                .doOnError(WebClientResponseException.class, ex -> logger.info("{}", ex.getResponseBodyAs(ProblemDetail.class))) // if you want actual error response from client
                .onErrorReturn(WebClientResponseException.InternalServerError.class, new CalculatorResponse(0,0, null, 0.0))
                .onErrorReturn(WebClientResponseException.BadRequest.class, new CalculatorResponse(0,0, null, -1.0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }


    @Test
    public void exchange()  {
        this.webClient.get()
                .uri("/lec05/calculator/{a}/{b}",10,20)
                .header("operation","*")
                .exchangeToMono(this::decode)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }


    private Mono<CalculatorResponse> decode(ClientResponse clientResponse){
//        clientResponse.cookies();
//        clientResponse.headers();

        logger.info("status code : {}", clientResponse.statusCode());
        if(clientResponse.statusCode().isError()){
            return clientResponse.bodyToMono(ProblemDetail.class)
                    .doOnNext(pd -> logger.info("{}", pd))
                    .then(Mono.empty());
        }

        return clientResponse.bodyToMono(CalculatorResponse.class);
    }
}
