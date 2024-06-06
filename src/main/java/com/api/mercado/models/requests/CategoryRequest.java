package com.api.mercado.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Categoria", example = "{\"name\":\"Beleza & Cuidados Pessoais\",\"description\":\"Produtos de beleza e cuidados pessoais\"}")
public record CategoryRequest(

    @NotBlank(message = "Nome não pode estar em branco") 
    @Size(message = "O tamanho máximo para nome é de 255 caracteres", max = 255) 
    String name,

    @NotBlank(message = "Descrição não pode estar em branco") 
    @Size(message = "O tamanho máximo para nome é de 255 caracteres", max = 255) 
    String description

) {}
