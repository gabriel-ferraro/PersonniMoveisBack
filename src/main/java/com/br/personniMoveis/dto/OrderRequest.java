package com.br.personniMoveis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dto pode receber uma lista de prods ou cmps a serem comprados.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private List<RequestProduct> requestProduct;
    private List<RequestCmp> requestCmp;
}
