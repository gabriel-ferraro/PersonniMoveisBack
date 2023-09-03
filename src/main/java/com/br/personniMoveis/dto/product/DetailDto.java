package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailDto {

    private Long detailId;
    private String detailField;
    private String fieldContent;
}
