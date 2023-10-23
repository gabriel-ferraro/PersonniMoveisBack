package com.br.personniMoveis.model.productCmp;

import com.br.personniMoveis.model.product.Section;
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
@Table(name = "element_cmp")
public class ElementCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "element_cmp_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String type;

    private Boolean mandatory;

    private Integer index;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private SectionCmp sectionCmp;

    @OneToMany
    @JoinColumn(name = "element_cmp_id")
    private Set<OptionCmp> optionCmps;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
