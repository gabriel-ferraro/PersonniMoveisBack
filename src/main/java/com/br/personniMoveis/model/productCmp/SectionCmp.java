package com.br.personniMoveis.model.productCmp;

import com.br.personniMoveis.model.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
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
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    /**
     * índice para renderizar a imagem na ordem esperada.
     */
    private Integer index;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany
    @JoinColumn(name = "section_cmp_id")
    private Set<ElementCmp> elementCmps;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
