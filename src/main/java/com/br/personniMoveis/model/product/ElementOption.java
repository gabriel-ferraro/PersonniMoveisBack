package com.br.personniMoveis.model.product;

import com.br.personniMoveis.constant.FieldType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mapeamento ORM para opção de elemento de um produto editável.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "element_option")
public class ElementOption {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "element_option_id")
    private Long elementOptionId;
    
    @Column(name = "option_name", nullable = false)
    String optionName;

    @Column(name = "option_img_url")
    String optionImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "field_type", nullable = false)
    private FieldType fieldType;
}
