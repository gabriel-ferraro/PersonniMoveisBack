package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class DetailDto {

    @NonNull
    private String detailField;
    @NonNull
    private String fieldContent;
}
