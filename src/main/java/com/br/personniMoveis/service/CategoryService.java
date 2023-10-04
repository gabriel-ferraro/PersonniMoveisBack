package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.CategoryCmpDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
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
import com.br.personniMoveis.service.productCmp.SectionCmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                           OptionCmpRepository optionCmpRepository) {
        this.categoryRepository = categoryRepository;
        this.sectionCmpService = sectionCmpService;
        this.sectionCmpRepository = sectionCmpRepository;
        this.elementCmpRepository = elementCmpRepository;
        this.optionCmpRepository = optionCmpRepository;
    }

    public Category findCategoryOrThrowNotFoundException(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Categoria não encontrada"));
    }

    public CategoryGetByIdDto findCategoryCmpByIdOrThrowBadRequestException(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada."));

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

    public void createCategoryCmp(CategoryCmpDto categoryCmpDto) {
        // Cria nova categoria.
        Category newCategory = CategoryMapper.INSTANCE.categoryCmpDtoToCategory(categoryCmpDto);
        // Persiste no BD.
        categoryRepository.save(newCategory);

        //Vê se tem alguma seção cadastrada junto com a categoria
        if (categoryCmpDto.getSectionCmpDtos() != null) {
            categoryCmpDto.getSectionCmpDtos().forEach(item -> {
                if (item.getName() != "") {
                    sectionCmpService.createSectionCmp(item, newCategory.getId());
                }
            });
        }
    }

    public void updateCategoryCmp(CategoryCmpDto categoryCmpDto, Long categoryId) {
        findCategoryOrThrowNotFoundException(categoryId);
        // Atualiza os dados da categoria
        Category updatedCategory = CategoryMapper.INSTANCE.categoryCmpDtoToCategory(categoryCmpDto);
        updatedCategory.setId(categoryId);
        categoryRepository.save(updatedCategory);

        // Atualiza seções existentes ou cria novas seções
        if (!categoryCmpDto.getSectionCmpDtos().isEmpty()) {
            for (SectionCmpDto sectionDto : categoryCmpDto.getSectionCmpDtos()) {
                if (sectionDto.getId() != null && sectionDto.getId() > 0) {
                    sectionCmpService.updateSectionCmp(sectionDto, sectionDto.getId());
                } else {
                    if (sectionDto.getName() != "" && sectionDto.getId() == 0) {
                        createNewSectionCmp(sectionDto, categoryId);
                    }
                }
            }
        }
    }

    //PARA CRIAÇÃO DA SEÇÃO CASO TENHA ELEMENTOS NA SEÇÃO
    private void createNewSectionCmp(SectionCmpDto sectionCmpDto, Long categoryId) {
        SectionCmpDto newSectionCmp = new SectionCmpDto();
        newSectionCmp.setName(sectionCmpDto.getName());
        newSectionCmp.setImgUrl(sectionCmpDto.getImgUrl());
        newSectionCmp.setElementCmpDtos(sectionCmpDto.getElementCmpDtos());
        sectionCmpService.createSectionCmp(newSectionCmp, categoryId);
    }

    public void deleteCategoryById(Long id) {
        Category categoryToDelete = findCategoryOrThrowNotFoundException(id);
        // Verifica se há seções relacionadas à categoria
        Set<SectionCmp> sectionsWithCategory = sectionCmpRepository.findByCategoryId(id);
        if (!sectionsWithCategory.isEmpty()) {
            throw new BadRequestException("Não é possível deletar categoria, existem seções associadas.");
        }
        // Deleta a categoria
        categoryRepository.delete(categoryToDelete);
    }

    public List<CategoryGetDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryMapper.INSTANCE::CategoryToCategoryGetDto).toList();
    }

    public List<ProductGetDto> getAllProductsInCategory(Long categoryId) {
        findCategoryOrThrowNotFoundException(categoryId);
        return categoryRepository.getAllProductsInCategory(categoryId);
    }
}
