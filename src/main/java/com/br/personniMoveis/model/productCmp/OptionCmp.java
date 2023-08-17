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
@Table(name = "option_cmp")
public class OptionCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "option_cmp_id")
    private Long optionCmpId;
    @Column(nullable = false)
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(nullable = false)
    private Double price;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "optionCmps")
//    private final Set<ElementCmp> elementCmps = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(name = "options_type_cmp", joinColumns = @JoinColumn(name = "option_cmp_id"), inverseJoinColumns = @JoinColumn(name = "type_cmp_id"))
//    private final Set<TypeCmp> typeCmps = new HashSet<>();
}
