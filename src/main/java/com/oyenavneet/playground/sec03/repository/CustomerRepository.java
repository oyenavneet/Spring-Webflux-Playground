package com.oyenavneet.playground.sec03.repository;

import com.oyenavneet.playground.sec03.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {


}
