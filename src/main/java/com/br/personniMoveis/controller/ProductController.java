package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.ProductPostDto;
import com.br.personniMoveis.dto.ProductPutDto;
import com.br.personniMoveis.model.Product;
import com.br.personniMoveis.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    //private final Environment environment;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(path = "home")
    public ResponseEntity<List<Product>> getHomeProducts() {
        return ResponseEntity.ok(productService.getHomeProductList());
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductPostDto productDto) {
        productService.createProduct(productDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody ProductPutDto productPutDto) {
        productService.updateProduct(productPutDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
