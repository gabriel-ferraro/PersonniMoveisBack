package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class DetailDto {

    private Long detailId;
    @NonNull
    private String detailField;
    @NonNull
    private String fieldContent;
}
