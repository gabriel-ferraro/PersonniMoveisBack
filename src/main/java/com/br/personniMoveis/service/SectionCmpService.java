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
import java.util.Set;
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

    public void createSectionCmp(Set<SectionCmpPostDto> sectionCmpPostDto, Long categoryId) {
        // Busca a categoria
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new BadRequestException("Category not found"));

        // Setando categorias para cada seção
        sectionCmpPostDto.forEach(item -> item.setCategoryId(category.getCategoryId()));
        //Salvando seção
        Set<SectionCmp> newSection = SectionCmpMapper.INSTANCE.toSectionCmpPost(sectionCmpPostDto);
        // Persiste a nova instância no banco de dados
        newSection.forEach(item -> sectionCmpRepository.save(item));
        
    }

    public void updateSectionCmp(Set<SectionCmpPutDto> sectionCmpPutDto, Long sectionCmpId) {
        // Faz alteracoes no produto.
        // Busca a categoria
        SectionCmp sectionCmp = sectionCmpRepository.findById(sectionCmpId).orElseThrow(() -> new BadRequestException("Section not found"));
        var category = sectionCmp.getCategoryId();
        // Setando categorias para cada seção
        sectionCmpPutDto.forEach(item -> item.setCategoryId(category));

        Set<SectionCmp> SectionBeUpdated = SectionCmpMapper.INSTANCE.toSectionCmpPut(sectionCmpPutDto);

        SectionBeUpdated.forEach(item -> sectionCmpRepository.save(item));
    }


    public void deleteSectionCmpById(Long sectionCmpId) {
        // Econtra produto ou joga exceção.
        findSectionByIdOrThrowBadRequestException(sectionCmpId, "Category not found");
        // Deleta produto via id.
        sectionCmpRepository.deleteById(sectionCmpId);
    }
}
