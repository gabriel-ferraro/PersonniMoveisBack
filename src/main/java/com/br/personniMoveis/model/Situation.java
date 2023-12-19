package com.br.personniMoveis.model;

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
@Table(name = "situation")
public class Situation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "situation_id")
    private Long situationId;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "request_situation", joinColumns = @JoinColumn(name = "requests_id"), inverseJoinColumns = @JoinColumn(name = "situation_id"))
    private final Set<Requests> requests = new HashSet<>();
}
