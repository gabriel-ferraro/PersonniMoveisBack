package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.Category.CategoryMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private SectionCmpService sectionCmpService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,SectionCmpService sectionCmpService){
        this.categoryRepository = categoryRepository;
        this.sectionCmpService = sectionCmpService;
    }

    public CategoryGetDto findCategoryByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        return CategoryMapper.INSTANCE.CategotyToCategoryGetDto(
                categoryRepository.findById(id).orElseThrow(
                        () -> new BadRequestException(exceptionMessage)));
    }

    //    public Page<ProductDto> getAllProducts(Pageable pageable) {
//        return productRepository.findAllProducts(pageable);
//    }
//    public Page<ProductDto> getAllProducts(Pageable pageable) {
//        return productRepository.findAllProducts(pageable);
//    }
//    public Page<ProductDto> getAllProducts(Pageable pageable) {
//        return productRepository.findAllProducts(pageable);
//    }
    public void createCategoryCmp(CategoryPostDto categoryPostDto) {
        // cria nova categoria.
        Category newCategory = CategoryMapper.INSTANCE.toCategoryPost(categoryPostDto);
        // persiste no BD.
        categoryRepository.save(newCategory);

        //Vê se tem alguma seção cadastrada junto com a categoria
        categoryPostDto.getSectionCmpPostDtos().forEach(item -> {
            if (item.getName() != "") {
                sectionCmpService.createSectionCmp(categoryPostDto.getSectionCmpPostDtos(), newCategory.getCategoryId());
            }
        });



    }

    public void updateCategory(CategoryPutDto categoryPutDto, Long categoryId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found");

        // Faz alteracoes no produto.
        Category CategoryBeUpdated = CategoryMapper.INSTANCE.toCategoryPut(categoryPutDto);

        CategoryBeUpdated.setCategoryId(categoryId);
        // Persiste alteracoes.
        categoryRepository.save(CategoryBeUpdated);

        //Vê se tem alguma seção cadastrada junto com a categoria
        categoryPutDto.getSectionCmpPutDtos().forEach(item -> {
            if (item.getName() != "" && item.getSectionCmpId() != null || item.getSectionCmpId() != 0 ) {
                sectionCmpService.updateSectionCmp(categoryPutDto.getSectionCmpPutDtos(), item.getSectionCmpId());
            }
        });
    }

    public void deleteCategoryById(Long categoryId) {
        // Econtra produto ou joga exceção.
        findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found");
        // Deleta produto via id.
        categoryRepository.deleteById(categoryId);
    }
}
