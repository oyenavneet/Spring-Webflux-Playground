package com.oyenavneet.playground.sec04.mapper;

import com.oyenavneet.playground.sec04.dto.CustomerDto;
import com.oyenavneet.playground.sec04.entity.Customer;

public class EntityDtoMapper {

    public static Customer toEntity(CustomerDto customerDto) {
        var customer = new Customer();
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setId(customerDto.id());
        return customer;
    }

    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}
