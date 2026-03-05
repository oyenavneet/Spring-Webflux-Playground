package com.oyenavneet.playground.sec09.service;

import com.oyenavneet.playground.sec09.dto.ProductDto;
import com.oyenavneet.playground.sec09.mapper.EntityDtoMapper;
import com.oyenavneet.playground.sec09.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Sinks.Many<ProductDto> sink;

    public Mono<ProductDto> saveProduct(Mono<ProductDto> mono) {
        return mono.map(EntityDtoMapper::toEntity)
                .flatMap(this.productRepository::save)
                .map(EntityDtoMapper::toDto)
                .doOnNext(this.sink::tryEmitNext); // when every we add a new product using sink we can emit/stream data
    }

    public Flux<ProductDto> productStream() {
        return this.sink.asFlux();
    }


}
