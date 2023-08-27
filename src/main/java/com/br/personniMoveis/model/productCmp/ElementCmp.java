package com.br.personniMoveis.model.productCmp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long sectionCmp;

    @OneToMany
    private Set<OptionCmp> optionCmps;

}
