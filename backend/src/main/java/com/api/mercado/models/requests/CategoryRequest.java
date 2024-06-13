package com.api.mercado.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(name = "Categoria", example = "{\"name\":\"Beleza & Cuidados Pessoais\",\"description\":\"Produtos de beleza e cuidados pessoais\"}")
public class CategoryRequest implements Cloneable {

    @NotBlank(message = "Nome é obrigatório") 
    @Size(message = "O tamanho máximo para nome é de 255 caracteres", max = 255) 
    private String name;

    @NotBlank(message = "Descrição é obrigatória") 
    @Size(message = "O tamanho máximo para descrição é de 255 caracteres", max = 255) 
    private String description;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception ex) {
            return null;
        }
    }

}
