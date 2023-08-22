package com.br.personniMoveis.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    
    @JsonIgnore
    @ManyToMany(mappedBy = "sections")
    private final List<Product> products = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "section_type", joinColumns = @JoinColumn(name = "section_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
    private final List<Type> types = new ArrayList<>();

}
