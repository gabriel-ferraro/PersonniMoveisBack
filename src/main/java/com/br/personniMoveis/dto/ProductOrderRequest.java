package com.br.personniMoveis.dto;

import com.br.personniMoveis.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto para adquirir os produtos do carrinho (produto ou cmp) e criar os pedidos no BD.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderRequest {

    /**
     * Produto selecionado
     */
    private Product product;

    /**
     * Quantidade de produtos selecionados.
     */
    private Long selectedAmount;
}
