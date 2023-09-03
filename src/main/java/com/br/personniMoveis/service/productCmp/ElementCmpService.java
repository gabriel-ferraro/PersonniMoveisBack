package com.br.personniMoveis.service.productCmp;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpGetDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.ElementCmp.ElementCmpMapper;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.OptionCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ElementCmpService {

    private final ElementCmpRepository elementCmpRepository;

    private final SectionCmpRepository sectionCmpRepository;

    private final OptionCmpService optionCmpService;

    private final OptionCmpRepository optionCmpRepository;


    @Autowired
    public ElementCmpService(ElementCmpRepository elementCmpRepository, SectionCmpRepository sectionCmpRepository,OptionCmpService optionCmpService, OptionCmpRepository optionCmpRepository)
    {
        this.elementCmpRepository = elementCmpRepository;
        this.sectionCmpRepository = sectionCmpRepository;
        this.optionCmpService = optionCmpService;
        this.optionCmpRepository = optionCmpRepository;
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

    public void createElementCmp(ElementCmpDto elementCmpDtos, Long sectionCmpId) {
            // Busca a seção
            SectionCmp sectionCmp = sectionCmpRepository.findById(sectionCmpId)
                    .orElseThrow(() -> new BadRequestException("Section not found"));
            // Converte e persiste os elementos
            ElementCmp newElement = ElementCmpMapper.INSTANCE.toElementCmp(elementCmpDtos);
            // Configura a seção nos elementos
            newElement.setSectionCmpId(sectionCmpId);
            // Persiste a nova instância no banco de dados
            elementCmpRepository.save(newElement);;

            // Criando elementos relacionados, se necessário
                for (OptionCmpDto optionCmpDto : elementCmpDtos.getOptionCmpDtos()) {
                    if (!optionCmpDto.getName().isEmpty()) {
                        optionCmpService.createOptionCmp(optionCmpDto, newElement.getId());
                    }
                }
    }


    public void updateElementCmp(ElementCmpDto elementCmpDtos, Long elementCmpId) {
        // Busca a categoria
        ElementCmp elementCmp = elementCmpRepository.findById(elementCmpId).orElseThrow(() -> new BadRequestException("Element not found"));
        // Atualiza os dados da seção
        ElementCmp ElementBeUpdated = ElementCmpMapper.INSTANCE.toElementCmp(elementCmpDtos);
        ElementBeUpdated.setSectionCmpId(elementCmp.getSectionCmpId());//Mantem a mesma seção
        ElementBeUpdated.setId(elementCmpId);
        // Persiste alteracoes.
        elementCmpRepository.save(ElementBeUpdated);

        for (OptionCmpDto optionCmpDto : elementCmpDtos.getOptionCmpDtos()) {
            if (optionCmpDto.getId() != null && optionCmpDto.getId() > 0) {
                optionCmpService.updateOptionCmp(optionCmpDto, optionCmpDto.getId());
            } else {
                createNewElementCmp(optionCmpDto, elementCmpId);
            }
        }
    }

    //PARA CRIAÇÃO DA OPÇÃO CASO TENHA OPÇÕES NO ELEMENTO
    private void createNewElementCmp(OptionCmpDto optionCmpDto, Long elementCmpId) {
        OptionCmpDto newOptionDto = new OptionCmpDto();
        newOptionDto.setName(optionCmpDto.getName());
        newOptionDto.setPrice(optionCmpDto.getPrice());
        newOptionDto.setImgUrl(optionCmpDto.getImgUrl());
        optionCmpService.createOptionCmp(optionCmpDto, elementCmpId);
    }

    public void deleteElementById(Long elementId) {
        ElementCmp elementToDelete = elementCmpRepository.findById(elementId)
                .orElseThrow(() -> new BadRequestException("Element not found"));

        // Verifica se há opções relacionadas
        Set<OptionCmp> optionWithElement = optionCmpRepository.findByElementCmpId(elementId);
        if (!optionWithElement.isEmpty()) {
            throw new BadRequestException("Cannot delete element. It has associated options.");
        }
        // Deleta o elemento
        elementCmpRepository.delete(elementToDelete);
    }
}
