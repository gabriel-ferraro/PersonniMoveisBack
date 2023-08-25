package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.product.post.CategoryProductPost;
import com.br.personniMoveis.mapper.Category.CategoryMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.service.CategoryService;
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

//    private final GenericFilterService<ProductCmp> genericFilterService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
//        this.genericFilterService = genericFilterService;
    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryGetDto> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(CategoryMapper.INSTANCE.CategotyToCategoryGetDto(
                categoryService.findCategoryOrThrowNotFoundException(categoryId)));
    }

    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping(path = "/create-full-product")
    public ResponseEntity<CategoryGetDto> createFullProduct(@RequestBody @Valid CategoryProductPost dto) {
        return ResponseEntity.ok(categoryService.createRegularProduct(dto));
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
