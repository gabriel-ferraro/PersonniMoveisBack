package com.br.personniMoveis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto pode receber uma lista de prods ou cmps a serem comprados.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private RequestProduct requestProduct;
    private RequestCmp requestCmp;
}
