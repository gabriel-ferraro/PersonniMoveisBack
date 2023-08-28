package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class TagDto {

    Long tagId;
    @NonNull
    private String tagName;
}
