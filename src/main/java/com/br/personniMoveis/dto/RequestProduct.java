package com.br.personniMoveis.dto;

import com.br.personniMoveis.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProduct {

    private Product product;
    private Long amount;
}
