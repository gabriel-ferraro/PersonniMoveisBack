package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.product.DetailDto;
import com.br.personniMoveis.dto.product.ProductDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.model.product.Detail;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.product.Tag;
import com.br.personniMoveis.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.hibernate.Remove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controladora do produto editável e produto convencional.
 */
@RestController
@RequestMapping("products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.findProductOrThrowNotFoundException(productId));
    }

    @Operation(summary = "Retorna lista de produtos mais recentes.", description = "Adquire os produtos mais recentemente" +
            " inclusos na loja. Se parâmetro opcional de qtde não for passado, retorna os últimos 4 produtos.")
    @GetMapping(path = "/most-recent")
    public ResponseEntity<List<Product>> getMostRecentProducts(@RequestParam(
            name = "amountOfProducts", required = false, defaultValue = "4") Integer amountOfProducts) {
        return ResponseEntity.ok(productService.getMostRecentProducts(amountOfProducts));
    }

    @Operation(summary = "Retorna lista de todos os produtos.")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("assign-tag/{productId}/{tagId}")
    public ResponseEntity<HttpStatus> assignTagToProduct(@PathVariable("productId") Long productId, @PathVariable("tagId") Long tagId) {
        productService.assignTagToProduct(productId, tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Cria e associa detail à um produto. Ao criar um detail, deve se associar à um produto existente.
     *
     * @param productId id do produto
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

    /**
     * Recebe o id de uma tag, retorna todos os produtos que possuem a tag.
     *
     * @param tagId Id de uma tag.
     * @return todos os produtos que possuem a tag do id indicado.
     */
    @Operation(summary = "Retorna produtos com a tag informada.", description = "Retorna produtos que possuem a tag de id informado.")
    @GetMapping("with-tag/{tagId}")
    public ResponseEntity<List<ProductGetDto>> getProductsWithTagById(@PathVariable(value = "tagId") Long tagId) {
        return ResponseEntity.ok(productService.getAllProductsWithTagId(tagId));
    }

    @Operation(summary = "Retorna todas as tags que o produto possui.", description = "Retorna produtos que possuem a tag de id informado.")
    @GetMapping("/{productId}/tags")
    public ResponseEntity<List<Tag>> getAllTagsInProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getAllTagsFromProduct(productId));
    }

    /**
     * Cria um único produto convencional sem a inclusão de categoria.
     *
     * @param productDto dto com dados do produto.
     * @return Produto simples sem dados adicionais persistido.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    /**
     * Cria um produto opcional completo (recebe payload com todos subitens) e retorna o produto persistido.
     *
     * @param categoryId Id da categoria (opcional, produto pode ser criado sem categoria).
     * @param product    (objeto do produto com seus subitens.
     * @return Retorna o produto persistido completo.
     */
    @Operation(summary = "Cria/edita produto convencional", description = "Endpoint que recebe todo payload para " +
            "criação ou edição do produto convencional como req param e seus subitens. Recebe um id opcional para setar a categoria do produto.")
    @PostMapping(path = "/save-full-product")
    public ResponseEntity<Product> createFullProduct(
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestBody @Valid Product product) {
        return ResponseEntity.ok(productService.saveRegularProduct(product, categoryId));
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

    @Operation(summary = "Deleta tag informada do produto.", description = "Deleta tag de id informado do produto de id informado.")
    @DeleteMapping(path = "delete-tag/{productId}/{tagId}")
    public ResponseEntity<HttpStatus> removeTagInProduct(@PathVariable("productId") Long productId, @PathVariable("tagId") Long tagId) {
        productService.removeTagInProduct(productId, tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Remove todas tags do produto.", description = "Remove todas tags do produto de id informado.")
    @DeleteMapping(path = "/{productId}/delete-all-tags")
    public ResponseEntity<HttpStatus> removeAllTagsInProduct(@PathVariable("productId") Long productId) {
        productService.removeAllTagsInProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
