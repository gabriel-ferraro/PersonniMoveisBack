package com.br.personniMoveis.model.productCmp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "img")
    private String img;

    @Column(nullable = false)
    private Double price;

    @NotNull
    private Long elementCmpId;
}
