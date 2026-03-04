package com.oyenavneet.playground.sec08.repository;

import com.oyenavneet.playground.sec08.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

}
