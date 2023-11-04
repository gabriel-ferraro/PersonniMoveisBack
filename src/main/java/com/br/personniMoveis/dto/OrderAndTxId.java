package com.br.personniMoveis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAndTxId {

    Long OrderId;
    Double totalValue;
}
