package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpGetDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.ElementCmp.ElementCmpMapper;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ElementCmpService {

    private final ElementCmpRepository elementCmpRepository;

    private final SectionCmpRepository sectionCmpRepository;

    private final OptionCmpService optionCmpService;


    @Autowired
    public ElementCmpService(ElementCmpRepository elementCmpRepository, SectionCmpRepository sectionCmpRepository,OptionCmpService optionCmpService)
    {
        this.elementCmpRepository = elementCmpRepository;
        this.sectionCmpRepository = sectionCmpRepository;
        this.optionCmpService = optionCmpService;

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

    public void createElementCmp(Set<ElementCmpDto> elementCmpDtos, Set<Long> sectionCmpIds) {
        for (Long sectionCmpId : sectionCmpIds) {
            // Busca a seção
            SectionCmp sectionCmp = sectionCmpRepository.findById(sectionCmpId)
                    .orElseThrow(() -> new BadRequestException("Section not found"));
            // Configura a seção nos elementos
            Set<ElementCmpDto> elementCmpDtosWithSection = elementCmpDtos.stream()
                    .peek(dto -> dto.setSectionCmpId(sectionCmp.getId()))
                    .collect(Collectors.toSet());
            // Converte e persiste os elementos
            Set<ElementCmp> newElements = ElementCmpMapper.INSTANCE.toElementCmp(elementCmpDtosWithSection);
            // Persiste a nova instância no banco de dados
            List<ElementCmp> newElementList = elementCmpRepository.saveAll(newElements);;

            //Busca todos os ids do elemento que foi criado
            Set<Long> elementIds = new HashSet<>();
            for (ElementCmp element : newElementList){
                elementIds.add(element.getId());
            }
            // Criando elementos relacionados, se necessário
            for (ElementCmpDto elementCmpDto : elementCmpDtos) {
                for (OptionCmpDto optionCmpDto : elementCmpDto.getOptionCmpDtos()) {
                    if (!optionCmpDto.getName().isEmpty()) {
                        optionCmpService.createOptionCmp(elementCmpDto.getOptionCmpDtos(), elementIds);
                    }
                }
            }
        }
    }


//    public void updateElementCmp(ElementCmpPutDto elementCmpPutDto, Long elementCmpId) {
//        // Faz alteracoes no produto.
//        // Busca a categoria
//        ElementCmp elementCmp = elementCmpRepository.findById(elementCmpId).orElseThrow(() -> new BadRequestException("Element not found"));
//        SectionCmp Section = elementCmp.getSectionCmp();
//        elementCmpPutDto.setSectionCmpId(Section.getSectionCmpId().longValue());
//
//        ElementCmp ElementBeUpdated = ElementCmpMapper.INSTANCE.toElementCmp(elementCmpPutDto);
//        ElementBeUpdated.setElementCmpId(elementCmpId);
//        // Persiste alteracoes.
//        elementCmpRepository.save(ElementBeUpdated);
//    }


    public void deleteElementCmpById(Long elementCmpId) {
        // Econtra produto ou joga exceção.
        findElementByIdOrThrowBadRequestException(elementCmpId, "Element not found");
        // Deleta produto via id.
        elementCmpRepository.deleteById(elementCmpId);
    }
}
