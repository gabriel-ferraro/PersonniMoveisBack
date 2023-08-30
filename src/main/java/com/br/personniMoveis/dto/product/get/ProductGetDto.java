package com.br.personniMoveis.dto.product.get;

import com.br.personniMoveis.dto.product.ProductDto;

public class ProductGetDto extends ProductDto {

    /**
     * Adquire somente os dados do produto, não adquirindo entidades relacionadas (passadas como no construtor como null).
     * @param productId
     * @param name
     * @param value
     * @param quantity
     * @param editable
     * @param imgUrl
     * @param description
     */
    public ProductGetDto(Long productId, String name, Double value, Long quantity, Boolean editable, String imgUrl, String description) {
        super(productId, name, value, quantity, editable, imgUrl, description, null, null, null ,null);
    }
}
