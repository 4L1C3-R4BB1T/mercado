package com.api.mercado.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.mercado.entities.Category;
import com.api.mercado.entities.Product;
import com.api.mercado.models.requests.CategoryRequest;
import com.api.mercado.repositories.CategoryRepository;
import com.api.mercado.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
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
    void givenAValidCategory_whenCallsCreate_thenShouldReturnCategoryDTO() {
        when(categoryRepository.save(any())).thenReturn(category);
        final var result = categoryService.create(categoryRequest);
        assertEquals(1L, result.getId());
        assertEquals(category.getName(), result.getName());
        assertEquals(category.getDescription(), result.getDescription());
    }

    @Test
    void givenAValidCategoryId_whenCallsUpdate_thenShouldReturnCategoryDTO() {
        final var expectedId = 1L;
        categoryRequest.setName("Updated Category");
        Category updatedCategory = (Category) category.clone();
        updatedCategory.setName(categoryRequest.getName());
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(updatedCategory);

        final var result = categoryService.update(1L, categoryRequest);

        assertEquals(expectedId, result.getId());
        assertEquals(categoryRequest.getName(), result.getName());
        assertEquals(categoryRequest.getDescription(), result.getDescription());
    }

    @Test
    void givenAInvalidCategoryId_whenCallsUpdate_thenShouldThrowException() {
        final var expectedId = 1L;
        final var expectedMessage = "Categoria com id " + expectedId + " não encontrada.";
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        final var exception = assertThrows(RuntimeException.class,
                () -> categoryService.update(expectedId, categoryRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void givenAValidCategoryId_whenCallsDelete_thenShouldReturnVoid() {
        final var expectedId = 1L;
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.findByCategory(any())).thenReturn(List.of());
        categoryService.delete(expectedId);
        verify(categoryRepository, times(1)).deleteById(argThat(arg -> Objects.equals(expectedId, arg)));
    }

    @Test
    void givenAValidCategoryId_whenCallsDeleteAndHasProductsAssociated_thenShouldThrowException() {
        final var expectedId = 1L;
        final var expectedMessage = "Não é permitido deletar categoria que está associada a um produto.";
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.findByCategory(any())).thenReturn(List.of(new Product(), new Product()));
        final var exception = assertThrows(RuntimeException.class, () -> categoryService.delete(expectedId));
        assertEquals(expectedMessage, exception.getMessage());
        verify(categoryRepository, never()).deleteById(argThat(arg -> Objects.equals(expectedId, arg)));
    }

}
