package com.oyenavneet.playground.sec03.controller;

import com.oyenavneet.playground.sec03.dto.CustomerDto;
import com.oyenavneet.playground.sec03.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable Integer id) {
        return this.customerService.getCustomerById(id);
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {
        return  this.customerService.saveCustomer(customerDtoMono);
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> updateCustomer( @PathVariable Integer id, @RequestBody Mono<CustomerDto> customerDtoMono) {
        return this.customerService.updateCustomer(id, customerDtoMono);
    }


    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return this.customerService.deleteCustomerById(id);
    }

}
