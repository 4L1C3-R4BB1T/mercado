package com.api.mercado.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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

    ProductRequest request;
    Product product;
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
    void testGivenAValidProductRequest_WhenCallsCreate_ThenShouldReturnProduct() {
        var expectedId = 1l;
        var expectedName = "Monitor";
        var expectedDescription = "Monitor 27 polegadas";

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(any())).thenReturn(product);

        ProductDTO result = productService.create(request);

        assertNotNull(result);
        assertEquals(expectedId, result.getId());
        assertEquals(expectedName, result.getName());
        assertEquals(expectedDescription, result.getDescription());
    }

    @Test
    void testGivenAValidProductRequest_WhenCallsUpdate_ThenShouldReturnProduct() {
        var expectedId = 1l;
        var expectedName = "Monitor Modificado";
        var expectedDescription = "Monitor 27 polegadas";
        
        Product clone = (Product) product.clone();
        clone.setName(expectedName);
        
        ProductRequest request = new ProductRequest(
                expectedName, clone.getDescription(), clone.getPrice(), clone.getStock(), 1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(clone);

        ProductDTO result = productService.update(1L, request);

        assertNotNull(result);
        assertEquals(expectedId, result.getId());
        assertEquals(expectedName, result.getName());
        assertEquals(expectedDescription, result.getDescription());
    }

    @Test
    void testGivenInvalidProductRequest_WhenCallsUpdate_ThenShouldBeThrowAnRuntimeException() {
        var expectedMessageError = "Categoria com id 1 não encontrada.";

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> productService.create(request));

        assertEquals(expectedMessageError, exception.getMessage());
    }

    @Test
    void testGivenInvalidProductIdRequest_WhenCallsUpdate_ThenShouldBeThrowAnRuntimeException() {
        var expectedMessageError = "Produto com id 1 não encontrado.";

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> productService.delete(1L));

        assertEquals(expectedMessageError, exception.getMessage());
    }

    @Test
    void testGivenAnValidId_WhenCallsDelete_ThenReturnVoid() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGivenAnInvalidId_WhenCallsDelete_ThenShouldBeThrowAnRuntimeException() {
        var expectedMessageError = "Product com id 1 não encontrado.";

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> productService.delete(1L));

        assertEquals(expectedMessageError, exception.getMessage());
    }

}
