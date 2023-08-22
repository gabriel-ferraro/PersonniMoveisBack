package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.ProductCmp.ProductCmpDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetDto;
//import com.br.personniMoveis.service.GenericFilterService;
import com.br.personniMoveis.service.ProductCmpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products_cmp")
public class ProductCmpController {

    private final ProductCmpService productCmpService;

//    private final GenericFilterService<ProductCmp> genericFilterService;

    @Autowired
    public ProductCmpController(ProductCmpService productCmpService) {
        this.productCmpService = productCmpService;
//        this.genericFilterService = genericFilterService;
    }



//    @GetMapping
//    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
//        return ResponseEntity.ok(productService.getAllProducts());
//    }

//    @GetMapping
//    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
//        return ResponseEntity.ok(productService.getAllProducts(pageable));
//    }

//    @GetMapping(path = "search")
//    public ResponseEntity<Page<Product>> searchProducts(Map<String, Object> ) {
//        return ResponseEntity.ok(productService.getAllProducts(pageable));
//    }

//    @GetMapping(path = "search")
//    public ResponseEntity<Page<ProductCmp>> searchProducts(
//            @RequestParam Map<String, Object> filters,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size
//    ) {
//        return ResponseEntity.ok(genericFilterService.findFilteredEntity(filters, page, size));
//    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductCmpDto productCmpDto) {
        productCmpService.createProductCmp(productCmpDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{product_cmp_id}")
    public ResponseEntity updateProduct(@RequestBody @Valid ProductCmpDto productCmpDto, @PathVariable("product_cmp_id") Long productCmpId) {
        productCmpService.updateProduct(productCmpDto, productCmpId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{product_cmp_id}")
    public ResponseEntity deleteProductById(@PathVariable("product_cmp_id") Long productCmpId) {
        productCmpService.deleteProductById(productCmpId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}