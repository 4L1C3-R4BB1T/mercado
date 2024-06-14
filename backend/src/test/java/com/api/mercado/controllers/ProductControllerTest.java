package com.api.mercado.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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
import com.api.mercado.entities.Product;
import com.api.mercado.models.ProductDTO;
import com.api.mercado.models.requests.ProductRequest;
import com.api.mercado.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private ProductService productService;

        Product product;
        ProductRequest request;
        Category category;

        @BeforeEach
        void setup() {
                request = new ProductRequest(
                                "Monitor", "Monitor 27 polegadas", BigDecimal.valueOf(760.0), 10, 1L);

                category = new Category();
                category.setId(1L);
                category.setName("Eletrônicos");
                category.setDescription("Produto que estraga rápido");

                product = new Product(1L, "Monitor", "Monitor 27 polegadas", BigDecimal.valueOf(760.0), 10, category);
        }

        @Test
        void givenProductRequest_whenCallsPost_thenReturnCreatedProductDTO() throws Exception {
                final var productDto = new ProductDTO(product);
                when(productService.create(any())).thenReturn(productDto);

                final var response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/v1/products")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(request)));

                response.andExpect(MockMvcResultMatchers.status().isCreated())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productDto.getId()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productDto.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDto.getDescription()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productDto.getPrice()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(productDto.getStock()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(category.getId()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value(category.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.description").value(category.getDescription()));
        }

        @Test
        void givenInvalidProductRequest_whenCallPost_thenShouldReturnAnObjectError() throws Exception {
                final var productDto = new ProductDTO(product);
                when(productService.create(any())).thenReturn(productDto);

                final var requestClone = (ProductRequest) request.clone();
                requestClone.setDescription(null);
                requestClone.setName(null);
                requestClone.setCategoryId(null);
                requestClone.setPrice(null);
                requestClone.setStock(null);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/v1/products")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(requestClone)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.price").isNotEmpty())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").isNotEmpty());
        }

        @Test
        void givenValidProductId_whenCallsFindById_thenShouldReturnGotProductDTO() throws Exception {
                final var expectedId = 1l;
                final var productDto = new ProductDTO(product);
                when(productService.findById(anyLong())).thenReturn(productDto);

                final var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}", expectedId));

                response.andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedId))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productDto.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDto.getDescription()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productDto.getPrice()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(productDto.getStock()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(category.getId()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value(category.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.description").value(category.getDescription()));
        }

        @Test
        void givenValidProductId_whenCallsUpdate_thenShouldReturnProductDTO() throws Exception {
                final var expectedId = 1l;
                final var productDto = new ProductDTO(product);
                productDto.setName("Updated Product");
                when(productService.update(anyLong(), any())).thenReturn(productDto);

                final var response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", expectedId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

                response.andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedId))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productDto.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDto.getDescription()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productDto.getPrice()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(productDto.getStock()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(category.getId()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value(category.getName()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.category.description").value(category.getDescription()));
        }

}
