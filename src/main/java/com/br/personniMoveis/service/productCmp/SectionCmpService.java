package com.br.personniMoveis.service.productCmp;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpGetDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.SectionCmp.SectionCmpMapper;
import com.br.personniMoveis.model.Category;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.CategoryRepository;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public SectionCmpService(SectionCmpRepository sectionCmpRepository, CategoryRepository categoryRepository,
                             ElementCmpService elementCmpService, ElementCmpRepository elementCmpRepository) {
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

    public void createSectionCmp(SectionCmpDto sectionCmpDto, Long categoryId) {
        // Busca a categoria
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BadRequestException("Category not found"));
        //Salvando seção
        SectionCmp newSection = SectionCmpMapper.INSTANCE.toSectionCmp(sectionCmpDto);
        newSection.setCategory(category);
        // Persiste a nova instância no banco de dados
        sectionCmpRepository.save(newSection);

        // Criando elementos relacionados, se necessário
                if (sectionCmpDto.getElementCmpDtos() != null) {
                    for (ElementCmpDto elementCmpDto: sectionCmpDto.getElementCmpDtos()) {
                            if (!elementCmpDto.getName().isEmpty()) {
                                elementCmpService.createElementCmp(elementCmpDto, newSection.getId());
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
        updatedSection.setCategory(sectionCmp.getCategory()); // Mantém a mesma categoria
        sectionCmpRepository.save(updatedSection);

        if (!sectionCmpDto.getElementCmpDtos().isEmpty()) {
            if (sectionCmpDto.getElementCmpDtos() != null) {
                for (ElementCmpDto elementDto : sectionCmpDto.getElementCmpDtos()) {
                    if (elementDto.getId() != null && elementDto.getId() > 0) {
                        elementCmpService.updateElementCmp(elementDto, elementDto.getId());
                    } else {
                        createNewElementCmp(elementDto, sectionCmpId);
                    }
                }
            }
        }
    }

    //PARA CRIAÇÃO DO ELEMENTO CASO TENHA ELEMENTOS NA SECTION
    private void createNewElementCmp(ElementCmpDto elementDto, Long sectionCmpId) {
        ElementCmpDto newElementDto = new ElementCmpDto();
        newElementDto.setName(elementDto.getName());
        newElementDto.setImgUrl(elementDto.getImgUrl());
        newElementDto.setIndex(elementDto.getIndex());
        newElementDto.setType(elementDto.getType());
        newElementDto.setOptionCmpDtos(elementDto.getOptionCmpDtos());
        elementCmpService.createElementCmp(newElementDto, sectionCmpId);
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
