package com.oyenavneet.playground.sec02;


import com.oyenavneet.playground.sec02.entity.Customer;
import com.oyenavneet.playground.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import reactor.test.StepVerifier;

public class Lec01CustomerRepositoryTest extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(Lec01CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void findAll() {
        this.customerRepository.findAll()
                .doOnNext(c -> logger.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();

    }

    @Test
    public void findById() {
        this.customerRepository.findById(2)
                .doOnNext(c -> logger.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("mike", c.getName()))
                .expectComplete()
                .verify();

    }


    @Test
    public void findByName() {
        this.customerRepository.findByName("jake")
                .doOnNext(c -> logger.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("jake@gmail.com", c.getEmail()))
                .expectComplete()
                .verify();

    }

    @Test
    public void findByEmailEndingWith() {
        this.customerRepository.findByEmailEndingWith("ke@gmail.com")
                .doOnNext(c -> logger.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("mike@gmail.com", c.getEmail()))
                .assertNext(c -> Assertions.assertEquals("jake@gmail.com", c.getEmail()))
                .expectComplete()
                .verify();

    }

    @Test
    public void insertAndDeleteCustomer() {
        //insert
        var newCustomer = new Customer();
        newCustomer.setName("sw");
        newCustomer.setEmail("sw@gmail.com");
        this.customerRepository.save(newCustomer)
                .doOnNext(c -> logger.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertNotNull(c.getId()))
                .expectComplete()
                .verify();


        //count
        this.customerRepository.count()
                .as(StepVerifier::create)
                .expectNext(11L)
                .expectComplete()
                .verify();


        //delete
        this.customerRepository.deleteById(11)
                .then(this.customerRepository.count())
                .as(StepVerifier::create)
                .expectNext(10L)
                .expectComplete()
                .verify();
    }



    //updating
    @Test
    public void updateCustomer() {
        this.customerRepository.findByName("sam")
                .doOnNext(c -> c.setName("sw")) // updating the name
                .flatMap(c -> this.customerRepository.save(c)) // saving to db
                .doOnNext(c -> logger.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("sw", c.getName()))
                .expectComplete()
                .verify();

    }

}
