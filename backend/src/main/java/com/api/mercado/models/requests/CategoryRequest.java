package com.api.mercado.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "Categoria", example = "{\"name\":\"Beleza & Cuidados Pessoais\",\"description\":\"Produtos de beleza e cuidados pessoais\"}")
public class CategoryRequest {

    @NotBlank(message = "Nome é obrigatório") 
    @Size(message = "O tamanho máximo para nome é de 255 caracteres", max = 255) 
    private String name;

    @NotBlank(message = "Descrição é obrigatória") 
    @Size(message = "O tamanho máximo para descrição é de 255 caracteres", max = 255) 
    private String description;

}
