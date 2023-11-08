package com.br.personniMoveis.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    public void updateFrom(ClientAddress updatedAddress) {
        if (updatedAddress.getAddressNickname() != null) {
            this.setAddressNickname(updatedAddress.getAddressNickname());
        }
        if (updatedAddress.getCEP() != null) {
            this.setCEP(updatedAddress.getCEP());
        }
        if (updatedAddress.getState() != null) {
            this.setState(updatedAddress.getState());
        }
        if (updatedAddress.getCity() != null) {
            this.setCity(updatedAddress.getCity());
        }
        if (updatedAddress.getDistrict() != null) {
            this.setDistrict(updatedAddress.getDistrict());
        }
        if (updatedAddress.getStreet() != null) {
            this.setStreet(updatedAddress.getStreet());
        }
        if (updatedAddress.getNumber() != null) {
            this.setNumber(updatedAddress.getNumber());
        }
        if (updatedAddress.getDetails() != null) {
            this.setDetails(updatedAddress.getDetails());
        }
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity clientAddress;

    @Override
    public int hashCode() {
        return Objects.hash(addressId);
    }
}
