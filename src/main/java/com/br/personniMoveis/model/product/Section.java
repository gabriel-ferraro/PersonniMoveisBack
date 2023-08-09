package com.br.personniMoveis.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Mapeamento ORM para elementos do produto (elementos que compõem o produto
 * editável).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "section_id")
    private Long section_id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "section_type", joinColumns = @JoinColumn(name = "section_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
    private final Set<Type> types = new HashSet<>();

}
