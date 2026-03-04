package com.oyenavneet.playground.sec08.service;

import com.oyenavneet.playground.sec08.dto.ProductDto;
import com.oyenavneet.playground.sec08.mapper.EntityDtoMapper;
import com.oyenavneet.playground.sec08.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> saveProduct(Flux<ProductDto> productDtoFlux) {
//        this.productRepository.saveAll(productDtoFlux.map(EntityDtoMapper::toEntity));

        return productDtoFlux.map(EntityDtoMapper::toEntity)
                .as(this.productRepository::saveAll)
                .map(EntityDtoMapper::toDto);
    }


    public Mono<Long> getProductsCount() {
        return this.productRepository.count();
    }

    public Flux<ProductDto> getAllProducts() {
        return this.productRepository.findAll()
                .map(EntityDtoMapper::toDto);

    }
}
