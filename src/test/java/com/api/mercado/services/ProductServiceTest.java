package com.api.mercado.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.mercado.entities.Category;
import com.api.mercado.entities.Product;
import com.api.mercado.models.ProductDTO;
import com.api.mercado.models.requests.ProductRequest;
import com.api.mercado.repositories.CategoryRepository;
import com.api.mercado.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    // @Test
    // void testGivenProductRequest_ThenReturnCreateProduct() {
    //     ProductRequest request = new ProductRequest(
    //             "Monitor", "Monitor 27 polegadas", BigDecimal.valueOf(760.0), 10, 1L);

    //     Category category = new Category();
    //     // category.setId(1L);

    //     when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

    //     ProductDTO result = productService.create(request);

    //     assertNotNull(result);
    // }

    // @Test
    // void testGivenProductRequestAndCategoryNotExists_ThenReturnCategoryNotFound() {
    //     ProductRequest request = new ProductRequest(
    //             "Monitor", "Monitor 27 polegadas", BigDecimal.valueOf(760.0), 10, 1L);

    //     when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

    //     assertThrows(RuntimeException.class, () -> productService.create(request));
    // }

    // @Test
    // void testGivenProductId_ThenReturnProductNotFound() {
    //     when(productService.findById(1L)).thenReturn(Optional.empty());
    //     assertThrows(RuntimeException.class, () -> productService.create(request));
    // }

}
