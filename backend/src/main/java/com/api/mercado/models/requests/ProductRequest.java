package com.api.mercado.models.requests;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "Produto", example = "{\"name\":\"Creme Hidratante\",\"description\":\"Creme hidratante para pele seca\",\"price\":19.99,\"stock\":200,\"categoryId\":6}")
public class ProductRequest implements Cloneable {

    @NotBlank(message = "Nome é obrigatório") 
    @Size(message = "O tamanho máximo para nome é de 255 caracteres", max = 255) 
    private String name;

    @NotBlank(message = "Descrição é obrigatória") 
    @Size(message = "O tamanho máximo para descrição é de 255 caracteres", max = 255) 
    private String description;
                
    @NotNull(message = "Preço não pode ser nulo") 
    @PositiveOrZero(message = "Preço deve ser um número positivo ou zero")
    private BigDecimal price;

    @NotNull(message = "Estoque não pode ser nulo")
    @PositiveOrZero(message = "Estoque deve ser um número positivo ou zero")
    private Integer stock;

    @NotNull(message = "Categoria é obrigatória") 
    private Long categoryId;

    @Override 
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception exception) {
            return null;
        }
    }

}
