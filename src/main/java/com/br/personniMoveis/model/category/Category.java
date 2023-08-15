package com.br.personniMoveis.model.category;

import com.br.personniMoveis.model.budget.Budget;
import com.br.personniMoveis.model.productCmp.OptionCmp;
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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private Boolean allow_creation = true; // Permitir criação do produto que se encaixa na categoria.

    @ManyToMany
    @JoinTable(name = "budget_category", joinColumns = @JoinColumn(name = "budget_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private final Set<Budget> budgets = new HashSet<>();
}
