package com.api.mercado.models;

import com.api.mercado.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
