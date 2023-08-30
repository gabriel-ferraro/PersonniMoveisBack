package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.CategoryDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
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

    public Category findCategoryById(Long id, String exceptionMessage){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(exceptionMessage));
        return  category;
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
        findCategoryByIdOrThrowBadRequestException(categoryId, "Category not found");

        // Atualiza os dados da categoria
        Category updatedCategory = CategoryMapper.INSTANCE.toCategory(categoryDto);
        updatedCategory.setId(categoryId);
        categoryRepository.save(updatedCategory);

        // Atualiza seções existentes ou cria novas seções
        for (SectionCmpDto sectionDto : categoryDto.getSectionCmpsDtos()) {
            if (sectionDto.getId() != null && sectionDto.getId() > 0) {
                sectionCmpService.updateSectionCmp(sectionDto, sectionDto.getId());
            } else {
                sectionCmpService.createSectionCmp(categoryDto.getSectionCmpsDtos(), categoryId);
            }
        }
    }



    public void deleteCategoryById(Long categoryId) {
        Category categoryToDelete = findCategoryById(categoryId, "Category not found");

        // Verifica se há seções relacionadas à categoria
        Set<SectionCmp> sectionsWithCategory = sectionCmpRepository.findByCategoryId(categoryId);
        if (!sectionsWithCategory.isEmpty()) {
            throw new BadRequestException("Cannot delete category. It has associated sections.");
        }

        // Deleta a categoria
        categoryRepository.delete(categoryToDelete);
    }



}
