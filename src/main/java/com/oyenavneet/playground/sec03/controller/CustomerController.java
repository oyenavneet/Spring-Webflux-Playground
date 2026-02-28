package com.oyenavneet.playground.sec03.controller;

import com.oyenavneet.playground.sec03.dto.CustomerDto;
import com.oyenavneet.playground.sec03.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<CustomerDto>> getCustomerById(@PathVariable Integer id) {
        return this.customerService.getCustomerById(id)
                .map(ResponseEntity::ok)// if you have data return ok, with response data
                .defaultIfEmpty(ResponseEntity.notFound().build()); // if not found return notFound
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {
        return this.customerService.saveCustomer(customerDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDto> customerDtoMono) {
        return this.customerService.updateCustomer(id, customerDtoMono)
                .map(ResponseEntity::ok)// if you have data return ok, with response data
                .defaultIfEmpty(ResponseEntity.notFound().build()); // if not found return notFound
    }


    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer id) {
        return this.customerService.deleteCustomerById(id)
                .filter(b -> b) // filtering if true
                .map(b -> ResponseEntity.ok().<Void>build()) //if true return ok
                .defaultIfEmpty(ResponseEntity.notFound().build()); // if false return notFound
    }

}
