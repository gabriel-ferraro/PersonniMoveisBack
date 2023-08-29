package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.*;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
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

    public void createCategoryCmp(CategoryDto categoryDto) {
        // cria nova categoria.
        Category newCategory = CategoryMapper.INSTANCE.toCategory(categoryDto);
        // persiste no BD.
        categoryRepository.save(newCategory);

        //Vê se tem alguma seção cadastrada junto com a categoria
        categoryDto.getSectionCmpsDtos().forEach(item -> {
            if (item.getName() != "") {
                sectionCmpService.createSectionCmp(categoryDto.getSectionCmpsDtos(), newCategory.getId());
            }
        });
    }

    public void updateCategory(CategoryDto categoryDto, Long categoryId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found");

        // Faz alteracoes no produto.
        Category CategoryBeUpdated = CategoryMapper.INSTANCE.toCategory(categoryDto);

        CategoryBeUpdated.setId(categoryId);
        // Persiste alteracoes.
        categoryRepository.save(CategoryBeUpdated);

        //Vê se tem alguma seção cadastrada junto com a categoria
        for (SectionCmpDto item : categoryDto.getSectionCmpsDtos()) {
            if (item.getName() != "" && item.getId() > 0) {

                SectionCmpDto sectionCmpDto = new SectionCmpDto();
                sectionCmpDto.setId(item.getId());
                sectionCmpDto.setName(item.getName());
                sectionCmpDto.setImgUrl(item.getImgUrl());
                sectionCmpDto.setElementCmpDtos(item.getElementCmpDtos());
                sectionCmpService.updateSectionCmp(sectionCmpDto, item.getId());

            } else {
                sectionCmpService.createSectionCmp(categoryDto.getSectionCmpsDtos(), categoryId);
            }
        }
    }

    public void deleteCategoryById(Long categoryId) {
        // Econtra produto ou joga exceção.
        findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found");
        // Deleta produto via id.
        categoryRepository.deleteById(categoryId);
    }

}
