package com.br.personniMoveis.dto;

import com.br.personniMoveis.model.productCmp.ProductCmp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCmp {
    
    private ProductCmp productCmp;
    private Long amount;
}
