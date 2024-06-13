package com.api.mercado.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.api.mercado.entities.Category;
import com.api.mercado.models.CategoryDTO;
import com.api.mercado.models.ProductDTO;
import com.api.mercado.models.requests.CategoryRequest;
import com.api.mercado.models.requests.ProductRequest;
import com.api.mercado.services.CategoryService;
import com.api.mercado.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    private Category category;

    private CategoryRequest categoryRequest;

    @BeforeEach
    public void setup() {
        category = new Category(1L, "Eletrônicos", "Conforto total em sua casa");
        categoryRequest = CategoryRequest.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Test
    void givenInvalidCategoryRequest_whenCallPost_thenShouldReturnAnObjectError() throws Exception {
        final var categoryDto = new CategoryDTO(category);
        when(categoryService.create(any())).thenReturn(categoryDto);

        final var categoryRequestClone = (CategoryRequest) categoryRequest.clone();
        categoryRequestClone.setDescription(null);
        categoryRequestClone.setName(null);
        // Termina isso fazendo os doutros
        
         mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestClone)))
                        .andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isNotEmpty());
    }

}
