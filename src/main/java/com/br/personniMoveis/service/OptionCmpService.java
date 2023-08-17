package com.br.personniMoveis.service;

import com.br.personniMoveis.repository.OptionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionCmpService {

    private final OptionCmpRepository optionCmpRepository;

    @Autowired
    public OptionCmpService(OptionCmpRepository optionCmpRepository){this.optionCmpRepository = optionCmpRepository;}

//    public CategoryGetDto findCategoryByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
//        return CategoryMapper.INSTANCE.CategotyToCategoryGetDto(
//                categoryRepository.findById(id).orElseThrow(
//                        () -> new BadRequestException(exceptionMessage)));
//    }
//
//    //    public Page<ProductDto> getAllProducts(Pageable pageable) {
////        return productRepository.findAllProducts(pageable);
////    }
////    public Page<ProductDto> getAllProducts(Pageable pageable) {
////        return productRepository.findAllProducts(pageable);
////    }
////    public Page<ProductDto> getAllProducts(Pageable pageable) {
////        return productRepository.findAllProducts(pageable);
////    }
//    public void createCategoryCmp(CategoryDto categoryDto) {
//        // cria novo produto.
//        Category newCategory = CategoryMapper.INSTANCE.toCategory(categoryDto);
//        // persiste no BD.
//        categoryRepository.save(newCategory);
//    }
//
//    public void updateCategory(CategoryDto categoryDto, Long categoryId) {
//        // Encontra produto existente para atualiza-lo ou joga exceção.
//        findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found");
//        // Faz alteracoes no produto.
//        Category CategoryBeUpdated = CategoryMapper.INSTANCE.toCategory(categoryDto);
//        CategoryBeUpdated.setCategoryId(categoryId);
//        // Persiste alteracoes.
//        categoryRepository.save(CategoryBeUpdated);
//    }
//
//    public void deleteCategoryById(Long categoryId) {
//        // Econtra produto ou joga exceção.
//        findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found");
//        // Deleta produto via id.
//        categoryRepository.deleteById(categoryId);
//    }
}
