package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailDto {

    private Long detailId;
    private String detailField;
    private String fieldContent;
    private CategoryDto categoryDto;
}
