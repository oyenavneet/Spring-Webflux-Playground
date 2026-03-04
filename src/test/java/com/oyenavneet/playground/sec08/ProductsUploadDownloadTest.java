package com.oyenavneet.playground.sec08;

import com.oyenavneet.playground.sec08.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.nio.file.Path;

public class ProductsUploadDownloadTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductsUploadDownloadTest.class);

    private final ProductClient productClient = new ProductClient();


    @Test
    public void upload() {

//        var flux = Flux.just(new ProductDto(null, "mouse", 100))
//                .delayElements(Duration.ofSeconds(10));

        var flux = Flux.range(1,1_000_000)
                .map(i ->new ProductDto(null, "product-"+i, i));
//                .delayElements(Duration.ofSeconds(2));

        this.productClient.uploadProduct(flux)
                .doOnNext(dto -> logger.info("received : {}", dto))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }



    @Test
    public void download() {
        this.productClient.downloadProducts()
                .map(ProductDto::toString)
                .as(flux -> FileWriter.create(flux, Path.of("products.txt")))
                .as(StepVerifier::create)
                .expectComplete()
                .verify();

    }
}
