package com.br.personniMoveis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequestDto {

    private String to;
    private String storeName;
    private String clientName;
    private String imgSource;
    private String redirectUrl;
}
