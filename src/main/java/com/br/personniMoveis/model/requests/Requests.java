package com.br.personniMoveis.model.requests;

import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.situation.Situation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany(mappedBy = "situation")
    private final List<Situation> situations = new ArrayList<>();
}
