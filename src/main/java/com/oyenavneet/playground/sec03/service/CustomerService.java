package com.oyenavneet.playground.sec03.service;

import com.oyenavneet.playground.sec03.dto.CustomerDto;
import com.oyenavneet.playground.sec03.mapper.EntityDtoMapper;
import com.oyenavneet.playground.sec03.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Flux<CustomerDto> getAllCustomers() {
        return this.customerRepository.findAll()
                .map(EntityDtoMapper::toDto);
    }


    public Mono<CustomerDto> getCustomerById(Integer id) {
        return this.customerRepository.findById(id)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> customerDtoMono) {
        return customerDtoMono.map(EntityDtoMapper::toEntity)
                .flatMap(this.customerRepository::save)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Integer id, Mono<CustomerDto> customerDtoMono) {
        return this.customerRepository.findById(id)
                .flatMap(entity -> customerDtoMono)
                .map(EntityDtoMapper::toEntity)
                .doOnNext(c -> c.setId(id))
                .flatMap(this.customerRepository::save)
                .map(EntityDtoMapper::toDto);
    }


    public Mono<Void> deleteCustomerById(Integer id) {
         return this.customerRepository.deleteById(id);
    }

}
