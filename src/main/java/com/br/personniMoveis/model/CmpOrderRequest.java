package com.br.personniMoveis.model;

import com.br.personniMoveis.model.productCmp.ProductCmp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto para adquirir os cmp do carrinho cmp e criar os pedidos no BD.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmpOrderRequest {

    /**
     * Cmp selecionado
     */
    private ProductCmp productCmp;

    /**
     * Quantidade de cmp selecionados.
     */
    private Long selectedAmount;
}
