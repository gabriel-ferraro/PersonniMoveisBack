package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.CategoryDto.CategoryCmpDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.model.Category;
import com.br.personniMoveis.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Categoria", description = "Adquire a categoria CMP do id informado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryGetByIdDto> getCategoryCmpById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.findCategoryCmpByIdOrThrowBadRequestException(id));
    }

    @Operation(summary = "Adquire produtos na categoria (CMP ou não)", description = "Lista todos os produtos da categoria de id informado")
    @GetMapping(path = "/products-in-category/{categoryId}")
    public ResponseEntity<List<ProductGetDto>> getAllProductsInCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getAllProductsInCategory(categoryId));
    }

    @Operation(summary = "Busca todas as Categorias", description = "Lista todas as categorias mas sem relacionamento")
    @GetMapping
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoryGetDto>> getAllCategoria() {
        List<CategoryGetDto> Category = categoryService.getAllCategories();
        return ResponseEntity.ok(Category);
    }

    @Operation(summary = "Cria Categoria", description = "Cria categoria com seções, elementos e opções")
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryCmpDto categoryCmpDto) {
        categoryService.createCategoryCmp(categoryCmpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza Categoria", description = "Atualiza categoria com seções, elementos e opções")
    @PutMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> updateProductCmp(@RequestBody @Valid CategoryCmpDto categoryCmpDto, @PathVariable("id") Long id) {
        categoryService.updateCategoryCmp(categoryCmpDto, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleta Categoria", description = "Deleta categoria se não tiver nenhum relacionametno")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteProductCmpById(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
