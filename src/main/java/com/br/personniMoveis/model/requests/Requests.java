package com.br.personniMoveis.model.requests;

import com.br.personniMoveis.model.budget.Budget;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.situation.Situation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Requests {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "requests_id")
    private Long requestId;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "requests")
    private final List<Situation> situations = new ArrayList<>();
}
