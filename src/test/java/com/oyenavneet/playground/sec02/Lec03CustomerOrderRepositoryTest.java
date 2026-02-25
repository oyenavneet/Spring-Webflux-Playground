package com.oyenavneet.playground.sec02;

import com.oyenavneet.playground.sec02.repository.CustomerOrderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec03CustomerOrderRepositoryTest extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(Lec03CustomerOrderRepositoryTest.class);

    @Autowired
    private CustomerOrderRepository customerOrderRepository;


    @Test
    public void productOrdersByCustomer(){

        this.customerOrderRepository.getProductsOrderedByCustomer("sam")
                .doOnNext(p -> logger.info("{}",p))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();


    }


    @Test
    public void getOrderDetailsByProduct(){

        this.customerOrderRepository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(p -> logger.info("{}",p))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();


    }

}
