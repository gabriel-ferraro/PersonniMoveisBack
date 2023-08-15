package com.br.personniMoveis.model.budget;

import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.model.productCmp.TypeCmp;
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
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "budget_id")
    private Long budgetId;

    @JsonIgnore
    @ManyToMany(mappedBy = "Budget")
    private final Set<Category> category = new HashSet<>();
}
