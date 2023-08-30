package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpGetDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.SectionCmp.SectionCmpMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.CategoryRepository;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SectionCmpService {

    private final SectionCmpRepository sectionCmpRepository;
    private final CategoryRepository categoryRepository;
    private final ElementCmpService elementCmpService;

    private final ElementCmpRepository elementCmpRepository;

    @Autowired
    public SectionCmpService(SectionCmpRepository sectionCmpRepository, CategoryRepository categoryRepository, ElementCmpService elementCmpService, ElementCmpRepository elementCmpRepository)
    {
        this.sectionCmpRepository = sectionCmpRepository;
        this.categoryRepository = categoryRepository;
        this.elementCmpService = elementCmpService;
        this.elementCmpRepository = elementCmpRepository;
    }

    public List<SectionCmpGetDto> getAllSections() {
        List<SectionCmp> sections = sectionCmpRepository.findAll();
        List<SectionCmpGetDto> sectionDtos = sections.stream()
                .map(SectionCmpMapper.INSTANCE::SectionToSectionGetDto)
                .collect(Collectors.toList());
        return sectionDtos;
    }

    public SectionCmpGetDto findSectionByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        return SectionCmpMapper.INSTANCE.SectionToSectionGetDto(
                sectionCmpRepository.findById(id).orElseThrow(
                        () -> new BadRequestException(exceptionMessage)));
    }

    public void createSectionCmp(Set<SectionCmpDto> sectionCmpDtos, Long categoryId) {
        // Busca a categoria
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BadRequestException("Category not found"));
        // Setando categorias para cada seção
        sectionCmpDtos.forEach(item -> item.setCategoryId(category.getId()));
        //Salvando seção
        Set<SectionCmp> newSection = SectionCmpMapper.INSTANCE.toSectionCmpList(sectionCmpDtos);
        // Persiste a nova instância no banco de dados
        List<SectionCmp> newSectionList = sectionCmpRepository.saveAll(newSection);;

        Set<Long> sectionsIds = new HashSet<>();
        for (SectionCmp section : newSectionList) {
            sectionsIds.add(section.getId());
        }
        // Criando elementos relacionados, se necessário
        for (SectionCmpDto sectionCmpDto : sectionCmpDtos) {
            for (ElementCmpDto elementCmpDto : sectionCmpDto.getElementCmpDtos()) {
                if (!elementCmpDto.getName().isEmpty()) {
                    elementCmpService.createElementCmp(sectionCmpDto.getElementCmpDtos(), sectionsIds);
                }
            }
        }
    }

    public void updateSectionCmp(SectionCmpDto sectionCmpDto, Long sectionCmpId) {
        SectionCmp sectionCmp = sectionCmpRepository.findById(sectionCmpId)
                .orElseThrow(() -> new BadRequestException("Section not found"));

        // Atualiza os dados da seção
        SectionCmp updatedSection = SectionCmpMapper.INSTANCE.toSectionCmp(sectionCmpDto);
        updatedSection.setId(sectionCmpId);
        updatedSection.setCategoryId(sectionCmp.getCategoryId()); // Mantém a mesma categoria
        sectionCmpRepository.save(updatedSection);

        for (ElementCmpDto elementDto : sectionCmpDto.getElementCmpDtos()) {
            if (elementDto.getId() != null && elementDto.getId() > 0) {
                elementCmpService.updateElementCmp(elementDto, elementDto.getId());
            } else {
                createNewElementCmp(elementDto, sectionCmpId);
            }
        }
    }

    //PARA CRIAÇÃO DO ELEMENTO CASO TENHA ELEMENTOS NA SECTION
    private void createNewElementCmp(ElementCmpDto elementDto, Long sectionCmpId) {
        ElementCmpDto newElementDto = new ElementCmpDto();
        newElementDto.setName(elementDto.getName());
        newElementDto.setImgUrl(elementDto.getImgUrl());
        newElementDto.setOptionCmpDtos(elementDto.getOptionCmpDtos());

        Set<ElementCmpDto> newElementSet = new HashSet<>();
        Set<Long> sectionIds = new HashSet<>();
        sectionIds.add(sectionCmpId);
        newElementSet.add(newElementDto);

        elementCmpService.createElementCmp(newElementSet, sectionIds);
    }


    public void deleteSectionById(Long sectionId) {
        SectionCmp sectionToDelete = sectionCmpRepository.findById(sectionId)
                .orElseThrow(() -> new BadRequestException("Section not found"));

        // Verifica se há elementos relacionados
        Set<ElementCmp> elementWithSection = elementCmpRepository.findBySectionCmpId(sectionId);
        if (!elementWithSection.isEmpty()) {
            throw new BadRequestException("Cannot delete section. It has associated element.");
        }
        // Deleta a seção
        sectionCmpRepository.delete(sectionToDelete);
    }
}
