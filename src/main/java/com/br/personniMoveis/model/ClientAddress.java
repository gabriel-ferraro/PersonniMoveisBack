package com.br.personniMoveis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ClientAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull
    private String addressNickname;
    
    @NotNull
    private String country;
    
    @NotNull
    private String city;
    
    @NotNull
    private String district;
    
    @NotNull
    private String street;
    
    @NotNull
    private String number;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity clientAddress;
}
