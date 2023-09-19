package com.br.personniMoveis.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "material_name", nullable = false)
    private String materialName;

    @Column(nullable = false)
    private Double price;

    @Column(name = "img_url")
    private String imgUrl;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "materials")
    private Set<Product> products;

    @Override
    public int hashCode() {
        return Objects.hash(materialId);
    }
}
