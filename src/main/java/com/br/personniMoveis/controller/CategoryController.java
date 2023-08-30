package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.CategoryDto.CategoryDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

//    private final GenericFilterService<ProductCmp> genericFilterService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
//        this.genericFilterService = genericFilterService;
    }

    @Operation(summary = "Categorias", description = "Lista todas as categorias")
    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> getAllCategoria() {
        List<CategoryGetDto> Category = categoryService.getAllCategory();
        if (Category.isEmpty()) {
            return ResponseEntity.ok(Category); // Retorna uma lista vazia
        }
        return ResponseEntity.ok(Category);
    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryGetByIdDto> getCategoryById (@PathVariable("categoryId") Long categoryId) {
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
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        categoryService.createCategoryCmp(categoryDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{cateogoryId}")
    public ResponseEntity updateProduct(@RequestBody @Valid CategoryDto categoryDto, @PathVariable("cateogoryId") Long cateogoryId) {
        categoryService.updateCategory(categoryDto, cateogoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{cateogoryId}")
    public ResponseEntity deleteProductById(@PathVariable("cateogoryId") Long cateogoryId) {
        categoryService.deleteCategoryById(cateogoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
