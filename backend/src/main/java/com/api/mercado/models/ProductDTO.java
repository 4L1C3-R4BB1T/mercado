package com.api.mercado.models;

import java.math.BigDecimal;

import com.api.mercado.entities.Category;
import com.api.mercado.entities.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Category category;

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        stock = entity.getStock();
        category = entity.getCategory();
    }

}
