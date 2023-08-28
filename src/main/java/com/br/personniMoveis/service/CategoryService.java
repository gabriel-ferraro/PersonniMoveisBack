package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.Category.CategoryMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.CategoryRepository;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.OptionCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final SectionCmpService sectionCmpService;

    private final SectionCmpRepository sectionCmpRepository;

    private final ElementCmpRepository elementCmpRepository;

    private final OptionCmpRepository optionCmpRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           SectionCmpService sectionCmpService,
                           SectionCmpRepository sectionCmpRepository,
                           ElementCmpRepository elementCmpRepository,
                            OptionCmpRepository optionCmpRepository){
        this.categoryRepository = categoryRepository;
        this.sectionCmpService = sectionCmpService;
        this.sectionCmpRepository = sectionCmpRepository;
        this.elementCmpRepository = elementCmpRepository;
        this.optionCmpRepository = optionCmpRepository;
    }


    public List<CategoryGetDto> getAllCategory() {
        List<Category> category = categoryRepository.findAll();
        List<CategoryGetDto> categoryDtos = category.stream()
                .map(CategoryMapper.INSTANCE::CategotyToCategoryGetDto)
                .collect(Collectors.toList());
        return categoryDtos;
    }


    public CategoryGetByIdDto findCategoryByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(exceptionMessage));

        Set<SectionCmp> sectionCmps = sectionCmpRepository.findByCategoryId(id);
        Set<ElementCmp> elementCmps = new HashSet<>();

        for (SectionCmp sectionCmp : sectionCmps) {
            Set<ElementCmp> sectionElementCmps = elementCmpRepository.findBySectionCmpId(sectionCmp.getId());
            elementCmps.addAll(sectionElementCmps);

            for (ElementCmp elementCmp : sectionElementCmps) {
                Set<OptionCmp> optionCmps = optionCmpRepository.findByElementCmpId(elementCmp.getId());
                elementCmp.setOptionCmps(optionCmps);
            }

            sectionCmp.setElementCmps(sectionElementCmps);
        }

        CategoryGetByIdDto categoryGetByIdDto = CategoryMapper.INSTANCE.CategotyToCategoryGetByIdDto(category);
        categoryGetByIdDto.setSectionCmps(sectionCmps);
        return categoryGetByIdDto;
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
        categoryPostDto.getSectionCmpDtos().forEach(item -> {
            if (item.getName() != "") {
                sectionCmpService.createSectionCmp(categoryPostDto.getSectionCmpDtos(), newCategory.getId());
            }
        });



    }

    public void updateCategory(CategoryPutDto categoryPutDto, Long categoryId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found");

        // Faz alteracoes no produto.
        Category CategoryBeUpdated = CategoryMapper.INSTANCE.toCategoryPut(categoryPutDto);

        CategoryBeUpdated.setId(categoryId);
        // Persiste alteracoes.
        categoryRepository.save(CategoryBeUpdated);

        //Vê se tem alguma seção cadastrada junto com a categoria
        categoryPutDto.getSectionCmpDtos().forEach(item -> {
            if (item.getName() != "" && item.getId() > 0 ) {
                sectionCmpService.updateSectionCmp(categoryPutDto.getSectionCmpDtos(), item.getId());
            }else{
                sectionCmpService.createSectionCmp(categoryPutDto.getSectionCmpDtos(), categoryId);
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
