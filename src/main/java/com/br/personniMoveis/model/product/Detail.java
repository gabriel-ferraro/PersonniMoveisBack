package com.br.personniMoveis.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Mapeamento ORM para detalhes do produto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detail")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "detail_id")
    private Long detailId;

    @Column(name = "detail_field")
    private String detailField;

    @Column(name = "field_content")
    private String fieldContent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public int hashCode() {
        return Objects.hash(detailId);
    }
}
