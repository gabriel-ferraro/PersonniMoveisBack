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

    @Operation(summary = "Busca todas as Categorias", description = "Lista todas as categorias mas sem relacionamento")
    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> getAllCategoria() {
        List<CategoryGetDto> Category = categoryService.getAllCategory();
        if (Category.isEmpty()) {
            return ResponseEntity.ok(Category); // Retorna uma lista vazia
        }
        return ResponseEntity.ok(Category);
    }

    @Operation(summary = "Busca categoria por Id com relacionamentos", description = "Lista todas as categorias mas com relacionamento")
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

    @Operation(summary = "Cria Categoria", description = "Cria categoria com seções, elementos e opções")
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        categoryService.createCategoryCmp(categoryDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza Categoria", description = "Atualiza categoria com seções, elementos e opções")
    @PutMapping(path = "/{cateogoryId}")
    public ResponseEntity updateProduct(@RequestBody @Valid CategoryDto categoryDto, @PathVariable("cateogoryId") Long cateogoryId) {
        categoryService.updateCategory(categoryDto, cateogoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleta Categoria", description = "Deleta categoria se não tiver nenhum relacionametno")
    @DeleteMapping(path = "/{cateogoryId}")
    public ResponseEntity deleteProductById(@PathVariable("cateogoryId") Long cateogoryId) {
        categoryService.deleteCategoryById(cateogoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
