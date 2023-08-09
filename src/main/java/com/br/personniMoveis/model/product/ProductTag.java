package com.br.personniMoveis.model.product;

import com.br.personniMoveis.model.pk.ProductTagPk;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_tag")
public class ProductTag {

    @EmbeddedId
    private ProductTagPk id = new ProductTagPk();

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("tag_id")
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
