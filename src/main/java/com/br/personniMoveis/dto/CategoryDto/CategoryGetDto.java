package com.br.personniMoveis.dto.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryGetDto {

    private Long id;
    private String name;
    private Boolean allow_creation; // Permitir criação do produto que se encaixa na categoria.
}
