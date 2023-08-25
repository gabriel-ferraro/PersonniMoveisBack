package com.br.personniMoveis.dto.product.get;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailGetDto {

    private String detail_id;
    private String detailField;
    private String fieldContent;
}
