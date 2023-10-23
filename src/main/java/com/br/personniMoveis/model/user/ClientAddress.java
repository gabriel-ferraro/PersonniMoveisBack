package com.br.personniMoveis.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Mapeamento ORM para endereço do cliente.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_address")
public class ClientAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "address_nickname", nullable = false)
    private String addressNickname;

    @Column(name = "cep", nullable = false)
    private String CEP;

    @Column(nullable = false)
    private String state;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity clientAddress;

    @Override
    public int hashCode() {
        return Objects.hash(addressId);
    }
}
