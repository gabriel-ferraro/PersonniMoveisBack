package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpGetDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPostDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPutDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.ElementCmp.ElementCmpMapper;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElementCmpService {

    private final ElementCmpRepository elementCmpRepository;

    private final SectionCmpRepository sectionCmpRepository;


    @Autowired
    public ElementCmpService(ElementCmpRepository elementCmpRepository, SectionCmpRepository sectionCmpRepository)
    {
        this.elementCmpRepository = elementCmpRepository;
        this.sectionCmpRepository = sectionCmpRepository;

    }

    public List<ElementCmpGetDto> getAllSections() {
        List<ElementCmp> elementCmps = elementCmpRepository.findAll();
        List<ElementCmpGetDto> elementCmpGetDtos = elementCmps.stream()
                .map(ElementCmpMapper.INSTANCE::ElementCmpToElementCmpGetDto)
                .collect(Collectors.toList());
        return elementCmpGetDtos;
    }

    public ElementCmpGetDto findElementByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        return ElementCmpMapper.INSTANCE.ElementCmpToElementCmpGetDto(
                elementCmpRepository.findById(id).orElseThrow(
                        () -> new BadRequestException(exceptionMessage)));
    }

    public void createElementCmp(ElementCmpPostDto elementCmpPostDto, Long sectionCmpId) {
        // Busca a categoria
        SectionCmp sectionCmp = sectionCmpRepository.findById(sectionCmpId).orElseThrow(() -> new BadRequestException("Section not found"));
        //seta a categoria na seção
        elementCmpPostDto.setSectionCmpId(sectionCmp.getSectionCmpId());

        ElementCmp newElement = ElementCmpMapper.INSTANCE.toElementCmp(elementCmpPostDto);

        // Persiste a nova instância no banco de dados
        elementCmpRepository.save(newElement);
    }

    public void updateElementCmp(ElementCmpPutDto elementCmpPutDto, Long elementCmpId) {
        // Faz alteracoes no produto.
        // Busca a categoria
        ElementCmp elementCmp = elementCmpRepository.findById(elementCmpId).orElseThrow(() -> new BadRequestException("Element not found"));
        SectionCmp Section = elementCmp.getSectionCmp();
        elementCmpPutDto.setSectionCmpId(Section.getSectionCmpId().longValue());

        ElementCmp ElementBeUpdated = ElementCmpMapper.INSTANCE.toElementCmp(elementCmpPutDto);
        ElementBeUpdated.setElementCmpId(elementCmpId);
        // Persiste alteracoes.
        elementCmpRepository.save(ElementBeUpdated);
    }


    public void deleteElementCmpById(Long elementCmpId) {
        // Econtra produto ou joga exceção.
        findElementByIdOrThrowBadRequestException(elementCmpId, "Element not found");
        // Deleta produto via id.
        elementCmpRepository.deleteById(elementCmpId);
    }
}
