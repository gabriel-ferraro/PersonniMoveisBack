package com.br.personniMoveis.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "main_img")
    private String mainImg;

    @Column
    private String description;

    @Column
    private Double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
