package com.oyenavneet.playground.sec08.controller;

import com.oyenavneet.playground.sec08.dto.ProductDto;
import com.oyenavneet.playground.sec08.dto.UploadResponse;
import com.oyenavneet.playground.sec08.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    private ProductService productService;

    @PostMapping(value = "upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductDto> flux) {
        logger.info("invoked");
       return this.productService.saveProduct(flux) // saving the product
//               .doOnNext(dto -> logger.info("received : {}", dto)) // printing to log
                .then(this.productService.getProductsCount()) // get tht count
                .map(count -> new UploadResponse(UUID.randomUUID(), count)); // map to UploadResponse dto
    }


    @GetMapping(value = "download" ,produces =  MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDto> downloadProducts() {
        return this.productService.getAllProducts();
    }


}
