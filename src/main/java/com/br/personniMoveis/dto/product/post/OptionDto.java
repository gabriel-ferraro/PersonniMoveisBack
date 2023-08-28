package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class OptionDto {

    @NonNull
    private String name;
    private String imgUrl;
    @NonNull
    private Double price;
}
