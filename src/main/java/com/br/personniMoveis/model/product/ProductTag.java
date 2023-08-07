package com.br.personniMoveis.model.product;

import com.br.personniMoveis.model.pk.ProductTagPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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
