package com.br.personniMoveis.model.pk;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Embeddable
@Data
public class ProductTagPk implements Serializable {

    private Long product_id;
    private Long tag_id;
}
