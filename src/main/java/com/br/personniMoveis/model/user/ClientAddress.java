package com.br.personniMoveis.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mapeamento ORM para endereço do cliente.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "adress_nickname", nullable = false)
    private String addressNickname;
    
    @Column(nullable = false)
    private String city;

    /**
     * Equivalente a bairro.
     */
    @Column(nullable = false)
    private String district;
    
    @Column(nullable = false)
    private String street;
    
    @Column(nullable = false)
    private String number;
    
    /**
     * Detalhes do endereço (referência ou detalhes).
     */
    private String details;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity clientAddress;
}
