package com.api.mercado.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.api.mercado.models.requests.CategoryRequest;
import com.api.mercado.services.CategoryService;
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

        private CategoryRequest request;

        @BeforeEach
        public void setup() {
                category = new Category(1L, "Eletrônicos", "Conforto total em sua casa");
                request = CategoryRequest.builder()
                                .name(category.getName())
                                .description(category.getDescription())
                                .build();
        }

        @Test
        void givenCategoryRequest_whenCallsPost_thenReturnCreatedCategoryDTO() throws Exception {
                final var categoryDto = new CategoryDTO(category);
                when(categoryService.create(any())).thenReturn(categoryDto);

                final var response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/v1/categories")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)));

                response.andExpect(MockMvcResultMatchers.status().isCreated())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryDto.getId()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(categoryDto.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(categoryDto.getDescription()));
        }

        @Test
        void givenInvalidCategoryRequest_whenCallPost_thenShouldReturnAnObjectError() throws Exception {
                final var categoryDto = new CategoryDTO(category);
                when(categoryService.create(any())).thenReturn(categoryDto);

                final var requestClone = (CategoryRequest) request.clone();
                requestClone.setDescription(null);
                requestClone.setName(null);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/v1/categories")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(requestClone)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isNotEmpty());
        }

        @Test
        void givenValidCategoryId_whenCallsFindById_thenShouldReturnGotCategoryDTO() throws Exception {
                final var expectedId = 1l;
                final var categoryDto = new CategoryDTO(category);
                when(categoryService.findById(anyLong())).thenReturn(categoryDto);

                final var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", expectedId));

                response.andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedId))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(categoryDto.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(categoryDto.getDescription()));
        }

        @Test
        void givenValidCategoryId_whenCallsUpdate_thenShouldReturnCategoryDTO() throws Exception {
                final var expectedId = 1l;
                final var categoryDto = new CategoryDTO(category);
                categoryDto.setName("Updated Category");
                when(categoryService.update(anyLong(), any())).thenReturn(categoryDto);

                final var response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/{id}", expectedId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

                response.andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedId))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(categoryDto.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(categoryDto.getDescription()));
        }

}
