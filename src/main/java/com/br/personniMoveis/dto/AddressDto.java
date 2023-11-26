package com.br.personniMoveis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressDto {

    private String addressNickname;
    private String cep;
    private String state;
    private String city;
    private String district;
    private String street;
    private String number;
    private String details;
}
