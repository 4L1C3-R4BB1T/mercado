package com.api.mercado.models.requests;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(name = "Produto", example = "{\"name\":\"Creme Hidratante\",\"description\":\"Creme hidratante para pele seca\",\"price\":19.99,\"stock\":200,\"categoryId\":6}")
public record ProductRequest(

    @NotBlank(message = "Nome não pode estar em branco") 
    @Size(message = "O tamanho máximo para nome é de 255 caracteres", max = 255) 
    String name,

    @NotBlank(message = "Descrição não pode estar em branco") 
    @Size(message = "O tamanho máximo para nome é de 255 caracteres", max = 255) 
    String description,
                
    @NotNull(message = "Preço não pode ser nulo") 
    @PositiveOrZero(message = "Preço deve ser um número positivo ou zero")
    BigDecimal price,

    @NotNull(message = "Estoque não pode ser nulo")
    @PositiveOrZero(message = "Estoque deve ser um número positivo ou zero")
    Integer stock,

    @NotNull(message = "Categoria é obrigatória") 
    Long categoryId

) {}
