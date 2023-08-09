package com.br.personniMoveis.model.pk;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class SectionTypePk implements Serializable {
    private Long section_id;
    private Long type_id;
}
