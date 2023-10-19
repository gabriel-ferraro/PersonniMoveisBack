package com.br.personniMoveis.model.productCmp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Lob
    @Column(name = "img", columnDefinition = "TEXT")
    private String img;

    @Column(nullable = false)
    private Double price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private ElementCmp elementCmp;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
