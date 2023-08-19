package com.br.personniMoveis.model.productCmp;

import com.br.personniMoveis.model.category.Category;
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
@Table(name = "section_cmp")
public class SectionCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "section_cmp_id")
    private Long sectionCmpId;

    @Column(nullable = false)
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    private Long categoryId;

    @OneToMany
    private final Set<ElementCmp> elementCmps = new HashSet<>();


//    @JsonIgnore
//    @ManyToMany(mappedBy = "sectionCmps")
//    private final Set<ProductCmp> productCmps = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(name = "section_element_cmp", joinColumns = @JoinColumn(name = "section_cmp_id"), inverseJoinColumns = @JoinColumn(name = "element_cmp_id"))
//    private final Set<ElementCmp> elementCmps = new HashSet<>();

}
