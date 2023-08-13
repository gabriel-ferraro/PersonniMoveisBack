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
 * Mapeamento ORM para opção de elemento de um produto editável.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "type_id")
    private Long typeId;

    @Column(nullable = false)
    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "types")
    private final List<Section> sections = new ArrayList<>();
}
