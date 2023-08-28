package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.dto.product.post.CategoryDto;
import com.br.personniMoveis.mapper.Category.CategoryMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.service.CategoryService;
import com.br.personniMoveis.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

//    private final GenericFilterService<ProductCmp> genericFilterService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryGetDto> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(CategoryMapper.INSTANCE.categoryToCategoryGetDto(
                categoryService.findCategoryOrThrowNotFoundException(categoryId)));
    }

    @GetMapping
    public ResponseEntity<List<com.br.personniMoveis.dto.product.get.CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping(path = "/products-in-category/{categoryId}")
    public ResponseEntity<List<ProductGetDto>> getAllProductsInCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getAllProductsInCategory(categoryId));
    }

    /**
     * Retorna uma categoria editada ou criada. Faz o processo de persistência de toda a categoria e objetos que ela contém.
     *
     * @param categoryId Id da categoria. Deve ser enviado para fazer a edição, não enviado para fazer a criação.
     * @param dto        Dto com os dados necessários para fazer a criação das entidades em somente uma requisição.
     * @return Uma categoria editada.
     */
    @PutMapping(path = "/create-full-product")
    public ResponseEntity<Category> createFullProduct(@PathVariable(required = false) Long categoryId, @RequestBody @Valid CategoryDto dto) {
        return ResponseEntity.ok(categoryService.createOrUpdateRegularProduct(categoryId, dto));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCategory(@RequestBody @Valid CategoryPostDto categoryPostDto) {
        categoryService.createCategoryCmp(categoryPostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{cateogoryId}")
    public ResponseEntity<HttpStatus> updateProduct(@RequestBody @Valid CategoryPutDto categoryPutDto, @PathVariable("cateogoryId") Long cateogoryId) {
        categoryService.updateCategory(categoryPutDto, cateogoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{cateogoryId}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("cateogoryId") Long cateogoryId) {
        categoryService.deleteCategoryById(cateogoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
