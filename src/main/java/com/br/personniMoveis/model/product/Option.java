package com.br.personniMoveis.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mapeamento ORM para tipos do campo de uma seção.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "option_id")
    private Long optionId;

    @Column(nullable = false)
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Section section;
}
