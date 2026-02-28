package com.oyenavneet.playground.sec03;

import com.oyenavneet.playground.sec03.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;
/*

    - Integration Testing
        - @AutoConfigureWebTestClient - Enable a WebTestClient instance for integration testing
        - It mock infrastructure with needing read HTTP server
        - allow testing endpoints with base URI

    Body vs BodyValue methods
    bodyValue -> When You have raw object ! (not a publisher type)
    body -> For publisher types
            i.g : Mono<CustomerDto> mono =
                : body(mono, CustomerDto.class

 */


@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec03")
public class CustomerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);

    @Autowired
    private WebTestClient client;


    // basic status code. HeadersType, and Response Object List Size validation
    @Test
    public void allCustomers() {
        this.client.get()
                .uri("/customers") // didn't need to pass BASE_PATH in uri @AutoConfigureWebTestClient do it for us.
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerDto.class)
                .value(list -> logger.info("{}", list))
                .hasSize(10);
    }


    // We can validate the JSON response using jsonPath()
    @Test
    public void paginateCustomers() {
        this.client.get()
                .uri("/customers/paginated?page=3&size=2")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(response -> logger.info("{}",new String(Objects.requireNonNull(response.getResponseBody()))))
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(5)
                .jsonPath("$[1].id").isEqualTo(6);
    }


    @Test
    public void customerById() {
        this.client.get()
                .uri("/customers/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(response -> logger.info("{}",new String(Objects.requireNonNull(response.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("sam")
                .jsonPath("$.email").isEqualTo("sam@gmail.com");
    }


    @Test
    public void createAndDeleteCustomer() {
        //create
        var dto = new CustomerDto(null, "sw", "sw@gmail.com");
        this.client.post()
                .uri("/customers")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(response -> logger.info("{}",new String(Objects.requireNonNull(response.getResponseBody()))))
                .jsonPath("$.id").isNumber() // if not sure about the value then we can check data type
                .jsonPath("$.name").isEqualTo("sw")
                .jsonPath("$.email").isEqualTo("sw@gmail.com");

        //delete
        this.client.delete()
                .uri("/customers/11")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().isEmpty();

    }


    @Test
    public void updateCustomer() {
        //update
        var dto = new CustomerDto(null, "sri", "sri@gmail.com");
        this.client.put()
                .uri("/customers/10")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(response -> logger.info("{}", new String(Objects.requireNonNull(response.getResponseBody()))))
                .jsonPath("$.id").isNumber() // if not sure about the value then we can check data type
                .jsonPath("$.name").isEqualTo("sri")
                .jsonPath("$.email").isEqualTo("sri@gmail.com");
    }


    @Test
    public void customerNotFound(){

        var dto = new CustomerDto(null, "sri", "sri@gmail.com");
        //get
        this.client.get()
                .uri("/customers/11")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();

        //delete
        this.client.delete()
                .uri("/customers/11")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();

        //put
        this.client.put()
                .uri("/customers/11")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();
    }

}
