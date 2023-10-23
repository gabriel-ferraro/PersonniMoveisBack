package com.br.personniMoveis.dto;

import com.br.personniMoveis.dto.ProductCmp.ProductCmpDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCmp {
    
    private ProductCmpDto productCmp;
    private Long amount;
}
