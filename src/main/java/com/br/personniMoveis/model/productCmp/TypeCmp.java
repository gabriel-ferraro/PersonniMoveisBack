package com.br.personniMoveis.model.productCmp;

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
@Table(name = "type_cmp")
public class TypeCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "type_cmp_id")
    private Long typeId;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "typeCmps")
    private final Set<SectionCmp> section_cmp = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "option_type_cmp", joinColumns = @JoinColumn(name = "option_cmp_id"), inverseJoinColumns = @JoinColumn(name = "type_cmp_id"))
    private final Set<OptionCmp> optionCmps = new HashSet<>();
}
