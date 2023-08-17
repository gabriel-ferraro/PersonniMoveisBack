package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPostDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpGetDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPutDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.SectionCmp.SectionCmpMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.CategoryRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionCmpService {

    private final SectionCmpRepository sectionCmpRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SectionCmpService(SectionCmpRepository sectionCmpRepository, CategoryRepository categoryRepository)
    {this.sectionCmpRepository = sectionCmpRepository;
        this.categoryRepository = categoryRepository;
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

    public void createSectionCmp(SectionCmpPostDto sectionCmpPostDto, Long categoryId) {
        // Busca a categoria
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BadRequestException("Category not found"));
        //seta a categoria na seção
        sectionCmpPostDto.setCategory(category);

        SectionCmp newSection = SectionCmpMapper.INSTANCE.toSectionCmp(sectionCmpPostDto);

        // Persiste a nova instância no banco de dados
        sectionCmpRepository.save(newSection);
    }

    public void updateSectionCmp(SectionCmpPutDto sectionCmpPutDto, Long sectionCmpId) {
        // Faz alteracoes no produto.
        // Busca a categoria
        SectionCmp sectionCmp = sectionCmpRepository.findById(sectionCmpId).orElseThrow(() -> new BadRequestException("Section not found"));
        var categoria = sectionCmp.getCategory();
        sectionCmpPutDto.setCategory(categoria);
        SectionCmp SectionBeUpdated = SectionCmpMapper.INSTANCE.toSectionCmp(sectionCmpPutDto);
        SectionBeUpdated.setSectionCmpId(sectionCmpId);
        // Persiste alteracoes.
        sectionCmpRepository.save(SectionBeUpdated);
    }


    public void deleteSectionCmpById(Long sectionCmpId) {
        // Econtra produto ou joga exceção.
        findSectionByIdOrThrowBadRequestException(sectionCmpId, "Category not found");
        // Deleta produto via id.
        sectionCmpRepository.deleteById(sectionCmpId);
    }
}
