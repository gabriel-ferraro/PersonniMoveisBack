package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.product.post.DetailDto;
import com.br.personniMoveis.dto.product.ProductDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.mapper.product.ProductMapper;
import com.br.personniMoveis.model.product.Detail;
import com.br.personniMoveis.model.product.Tag;
import com.br.personniMoveis.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controladora do produto editável e produto convencional.
 */
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
//    private final GenericFilterService<Product> genericFilterService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
//        this.genericFilterService = genericFilterService;
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductGetDto> getProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(ProductMapper.INSTANCE.productToProductGetDto(
                productService.findProductOrThrowNotFoundException(productId)));
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

    /**
     * Cria e associa detail à um produto. Ao criar um detail, deve se associar à um produto existente.
     *
     * @param productId     id do produto
     * @param detailDto dto para criação do detail.
     * @return Detail persistido e associado ao produto indicado.
     */
    @PostMapping("assign-detail/{productId}/")
    public ResponseEntity<Detail> assignDetailToProduct(@PathVariable("productId") Long productId, @RequestBody @Valid DetailDto detailDto) {
        return ResponseEntity.ok(productService.assignDetailToProduct(productId, detailDto));
    }

    /**
     * Remove um detalhe de um prduto e deleta o detalhe.
     *
     * @param productId Id do produto
     * @param detailId  id do detalhe.
     * @return Http status 201.
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> removeDetailInProduct(Long productId, Long detailId) {
        productService.removeDetailInProduct(productId, detailId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<HttpStatus> updateDetail(Long productId, Long detailId, DetailDto detailDto) {
        productService.updateDetail(productId, detailId, detailDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{productId}/tags")
    public ResponseEntity<List<Tag>> getAllTagsInProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getAllTagsFromProduct(productId));
    }

    @PostMapping
    public ResponseEntity<ProductGetDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
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
    public ResponseEntity<HttpStatus> removeAllTagsInProduct(@PathVariable("productId") Long productId) {
        productService.removeAllTagsInProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
