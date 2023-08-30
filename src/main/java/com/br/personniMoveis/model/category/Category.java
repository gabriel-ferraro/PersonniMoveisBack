package com.br.personniMoveis.model.category;

import com.br.personniMoveis.model.productCmp.SectionCmp;
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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private Boolean allow_creation = true; // Permitir criação do produto que se encaixa na categoria.


}
