package com.oyenavneet.playground.sec01;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

/*
    Simple Traditional Rest API Implementation
    - calling external service api which take 10 sec to response - so it block api for 10 sec to response
    - API take 10 sec to response , even the user canceled the request after 1 sec the service is still doing the work
    -

 */

@RestController
@RequestMapping("traditional")
public class TraditionalWebController {

    private static final Logger logger = LoggerFactory.getLogger(TraditionalWebController.class);

    private final RestClient restClient = RestClient.builder().baseUrl("http://localhost:7070").build();

    @GetMapping("products")
    public List<Product> getProducts() {
        var list = this.restClient.get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });

        logger.info("received response {}", list);
        return list;
    }


}
