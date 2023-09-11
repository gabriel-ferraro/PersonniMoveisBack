package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetByIdDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetDto;
import com.br.personniMoveis.service.ProductCmpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products_cmp")
@SecurityRequirement(name = "bearer-key")
public class ProductCmpController {

    private final ProductCmpService productCmpService;

//    private final GenericFilterService<ProductCmp> genericFilterService;

    @Autowired
    public ProductCmpController(ProductCmpService productCmpService) {
        this.productCmpService = productCmpService;
//        this.genericFilterService = genericFilterService;
    }


    @Operation(summary = "Busca produtos CMP por Id com relacionamentos", description = "Lista todas as Produtos CMP mas com relacionamento")
    @GetMapping(path = "/{product_cmp_id}")
    public ResponseEntity<ProductCmpGetByIdDto> ProductCmpGetById (@PathVariable("product_cmp_id") Long id) {
        return ResponseEntity.ok(productCmpService.findProdutctCmpByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@RequestBody @Valid ProductCmpDto productCmpDto) {
        productCmpService.createProductCmp(productCmpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{product_cmp_id}")
    public ResponseEntity<HttpStatus> updateProduct(@RequestBody @Valid ProductCmpDto productCmpDto, @PathVariable("product_cmp_id") Long productCmpId) {
        productCmpService.updateProduct(productCmpDto, productCmpId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{product_cmp_id}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("product_cmp_id") Long productCmpId) {
        productCmpService.deleteProductById(productCmpId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}