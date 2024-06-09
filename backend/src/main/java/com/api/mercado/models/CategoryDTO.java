package com.api.mercado.models;

import com.api.mercado.entities.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
    }

}
