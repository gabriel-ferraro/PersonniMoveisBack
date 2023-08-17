package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

//    private final GenericFilterService<ProductCmp> genericFilterService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
//        this.genericFilterService = genericFilterService;
    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryGetDto> getCategoryById (@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found"));
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
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryPostDto categoryPostDto) {
        categoryService.createCategoryCmp(categoryPostDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{cateogoryId}")
    public ResponseEntity updateProduct(@RequestBody @Valid CategoryPutDto categoryPutDto, @PathVariable("cateogoryId") Long cateogoryId) {
        categoryService.updateCategory(categoryPutDto, cateogoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{cateogoryId}")
    public ResponseEntity deleteProductById(@PathVariable("cateogoryId") Long cateogoryId) {
        categoryService.deleteCategoryById(cateogoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
