package com.oyenavneet.playground.sec09.repository;

import com.oyenavneet.playground.sec09.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

}
