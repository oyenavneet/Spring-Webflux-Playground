package com.oyenavneet.playground.sec02;

import com.oyenavneet.playground.sec02.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

public class Lec02ProductRepositoryTest extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(Lec02ProductRepositoryTest.class);

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void testFindProductByPriceBetween() {

        this.productRepository.findByPriceBetween(750, 1000)
                .doOnNext(product -> logger.info("{}", product))
                .as(StepVerifier::create)
                .expectNextCount(3)
                .expectComplete()
                .verify();

    }


    //similar to limit, offset and order by
    @Test
    public void pageable() {

        this.productRepository.findBy(PageRequest.of(0,3).withSort(Sort.by("price").ascending()))
                .doOnNext(product -> logger.info("{}", product))
                .as(StepVerifier::create)
                .expectNextCount(3)
                .expectComplete()
                .verify();

    }


}
