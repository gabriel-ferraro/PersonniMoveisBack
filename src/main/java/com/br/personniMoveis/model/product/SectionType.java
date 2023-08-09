package com.br.personniMoveis.model.product;

import com.br.personniMoveis.model.pk.ProductTagPk;
import com.br.personniMoveis.model.pk.SectionTypePk;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "section_type")
public class SectionType {

    @EmbeddedId
    private SectionTypePk id = new SectionTypePk();

    @ManyToOne
    @MapsId("section_id")
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @MapsId("type_id")
    @JoinColumn(name = "type_id")
    private Type type;
}
