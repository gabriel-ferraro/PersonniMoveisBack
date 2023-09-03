package com.br.personniMoveis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StorePropertiesDto {

    private String storeName;
    private String storeLogoPath;
    private String storeEmail;
    private String aboutUsInfo;
    private String storeAddress;
    private String storePhone;
    private String primaryCollor;
    private String secondaryCollor;
    private Boolean isClientOrderEvaluated;
}
