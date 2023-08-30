package com.br.personniMoveis.model.productCmp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "section_cmp")
public class SectionCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "section_cmp_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @NotNull
    private Long categoryId;

    @OneToMany
    private Set<ElementCmp> elementCmps;

}
