package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.dto.ProductCmp.ProductCmpOptionDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryPostDto {

    private Long categoryId;

    private String name;
}
