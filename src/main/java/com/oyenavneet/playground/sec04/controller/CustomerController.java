package com.oyenavneet.playground.sec04.controller;

import com.oyenavneet.playground.sec04.dto.CustomerDto;
import com.oyenavneet.playground.sec04.exceptions.ApplicationExceptions;
import com.oyenavneet.playground.sec04.service.CustomerService;
import com.oyenavneet.playground.sec04.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDto>> getAllCustomers(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "3") Integer size) {
        return this.customerService.getAllCustomers(page, size)
                .collectList();
    }

    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable Integer id) {
        return this.customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {
        return customerDtoMono.transform(RequestValidator.validate()) // validating if the request is value, when get error signal it go through Controller advice
                .as(this.customerService::saveCustomer);
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDto> customerDtoMono) {
        return customerDtoMono.transform(RequestValidator.validate()) //when get error signal it go through Controller advice
                .as(validRequest -> this.customerService.updateCustomer(id, customerDtoMono))
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));

//                this.customerService.updateCustomer(id, customerDtoMono)
//                .map(ResponseEntity::ok)// if you have data return ok, with response data
//                .defaultIfEmpty(ResponseEntity.notFound().build()); // if not found return notFound
    }


    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return this.customerService.deleteCustomerById(id)
                .filter(b -> b) // filtering if true
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .then();
    }

}
