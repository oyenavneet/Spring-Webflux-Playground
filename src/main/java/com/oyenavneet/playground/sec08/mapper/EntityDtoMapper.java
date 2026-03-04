package com.oyenavneet.playground.sec08.mapper;

import com.oyenavneet.playground.sec08.dto.ProductDto;
import com.oyenavneet.playground.sec08.entity.Product;

public class EntityDtoMapper {

    public static Product toEntity(ProductDto dto) {
        Product entity = new Product();
        entity.setId(dto.id());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        return entity;
    }

    public static ProductDto toDto(Product entity) {
        return new ProductDto(
                entity.getId(),
                entity.getDescription(),
                entity.getPrice()
        );
    }
}
