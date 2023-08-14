package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.ProductDto;
import com.br.personniMoveis.dto.ProductGetDto;
import com.br.personniMoveis.mapper.ProductMapper;
import com.br.personniMoveis.model.product.Product;
//import com.br.personniMoveis.service.GenericFilterService;
import com.br.personniMoveis.service.ProductService;
import com.br.personniMoveis.service.TagService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//    private final GenericFilterService<Product> genericFilterService;

    @Autowired
    public ProductController(ProductService productService, TagService tagService) {
        this.productService = productService;
//        this.genericFilterService = genericFilterService;
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductGetDto> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(ProductMapper.INSTANCE.productToProductGetDto(
                productService.findProductByIdOrThrowNotFoundException(productId)));
    }

    @GetMapping
    public ResponseEntity<Page<ProductGetDto>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

//    @GetMapping(path = "/search")
//    public ResponseEntity<Page<ProductGetDto>> searchProducts(
//            @RequestParam Map<String, Object> filters,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size
//    ) {
//        return ResponseEntity.ok(genericFilterService.findFilteredEntity(filters, page, size)
//                .map(ProductMapper.INSTANCE::productToProductGetDto));
//    }

    /**
     * Recebe o id de uma tag, retorna todos os produtos que possuem a tag.
     *
     * @param tagId Id de uma tag.
     * @return todos os produtos que possuem a tag do id indicado.
     */
    @GetMapping("with-tag/{tagId}")
    public ResponseEntity<List<ProductGetDto>> getProductsWithTagById(@PathVariable(value = "tagId") Long tagId) {
        return ResponseEntity.ok(productService.getAllProductsWithTagId(tagId));
    }

    @PostMapping("assign-tag/{productId}/{tagId}")
    public ResponseEntity<HttpStatus> assignTagToProduct(@PathVariable("productId") Long productId, @PathVariable("tagId") Long tagId) {
        productService.assignTagToProduct(productId, tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@RequestBody @Valid ProductDto productDto) {
        productService.createProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{productId}")
    public ResponseEntity<HttpStatus> updateProduct(@RequestBody @Valid ProductDto productDto, @PathVariable("productId") Long productId) {
        productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("productId") Long productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "delete-tag/{productId}/{tagId}")
    public ResponseEntity<HttpStatus> removeTagInProduct(@PathVariable("productId") Long productId, @PathVariable("tagId") Long tagId) {
        productService.removeTagInProduct(productId, tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{productId}/delete-all-tags")
    public ResponseEntity<HttpStatus> removeAllInProduct(@PathVariable("productId") Long productId) {
        productService.removeAllTagsInProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
