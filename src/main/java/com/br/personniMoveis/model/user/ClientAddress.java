package com.br.personniMoveis.model.user;

import jakarta.persistence.*;
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
    @Column(name = "address_id")
    private Long addressId;

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
