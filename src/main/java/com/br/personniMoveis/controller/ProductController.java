package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.ProductPostDto;
import com.br.personniMoveis.dto.ProductPutDto;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.service.GenericFilterService;
import com.br.personniMoveis.service.ProductService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controladora do produto editável e produto convencional.
 */
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    private final GenericFilterService<Product> genericFilterService;

    @Autowired
    public ProductController(ProductService productService, GenericFilterService<Product> genericFilterService) {
        this.productService = productService;
        this.genericFilterService = genericFilterService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }
    
//    @GetMapping(path = "search")
//    public ResponseEntity<Page<Product>> searchProducts(Map<String, Object> ) {
//        return ResponseEntity.ok(productService.getAllProducts(pageable));
//    }
    
    @GetMapping(path = "search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam Map<String, Object> filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(genericFilterService.findFilteredEntity(filters, page, size));
    }
    
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductPostDto productDto) {
        productService.createProduct(productDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{productId}")
    public ResponseEntity updateProduct(@RequestBody @Valid ProductPutDto productPutDto, @PathVariable("productId") Long productId) {
        productService.updateProduct(productPutDto, productId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{productId}")
    public ResponseEntity deleteProductById(@PathVariable("productId") Long productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
