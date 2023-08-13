package com.br.personniMoveis.model.productCmp;

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
@Table(name = "section_cmp")
public class SectionCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "section_cmp_id")
    private Long section_cmp_id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "section_type_cmp", joinColumns = @JoinColumn(name = "section_cmp_id"), inverseJoinColumns = @JoinColumn(name = "type_cmp_id"))
    private final Set<TypeCmp> typeCmps = new HashSet<>();

}