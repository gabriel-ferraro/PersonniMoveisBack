package com.br.personniMoveis.model.productCmp;

import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.product.Section;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "element_cmp")
public class ElementCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "element_cmp_id")
    private Long elementCmpId;

    @Column(nullable = false)
    private String name;

    private String type;

    @ManyToOne
    @JoinColumn(name = "section_cmp_id")
    private SectionCmp sectionCmp;
}
