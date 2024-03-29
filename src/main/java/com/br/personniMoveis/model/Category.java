package com.br.personniMoveis.model;

import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    private Boolean allow_creation; // Permitir criação do produto que se encaixa na categoria.

    /**
     * Flag para fazer exclusão lógica - determina se cmp foi removido:
     * (não pode ser mais comprado, não parece mais em pesquisas).
     */
    @Column
    private Boolean isRemoved;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "id")
    private Set<Product> products;

    @OneToMany
    @JoinColumn(name = "id")
    private Set<SectionCmp> sectionCmp;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
