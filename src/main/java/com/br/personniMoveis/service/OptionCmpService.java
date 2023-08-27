package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpGetDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPostDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPutDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.ElementCmp.ElementCmpMapper;
import com.br.personniMoveis.mapper.OptionCmp.OptionCmpMapper;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.OptionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OptionCmpService {


    private final ElementCmpRepository elementCmpRepository;

    private final OptionCmpRepository optionCmpRepository;


    @Autowired
    public OptionCmpService(ElementCmpRepository elementCmpRepository, OptionCmpRepository optionCmpRepository)
    {
        this.elementCmpRepository = elementCmpRepository;
        this.optionCmpRepository = optionCmpRepository;

    }

    public List<OptionCmpGetDto> getAllOptions() {
        List<OptionCmp> optionCmps = optionCmpRepository.findAll();
        List<OptionCmpGetDto> optionCmpGetDtos = optionCmps.stream()
                .map(OptionCmpMapper.INSTANCE::OptionCmpToOptionCmpGetDto)
                .collect(Collectors.toList());
        return optionCmpGetDtos;
    }

    public OptionCmpGetDto findOptionByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        return OptionCmpMapper.INSTANCE.OptionCmpToOptionCmpGetDto(
                optionCmpRepository.findById(id).orElseThrow(
                        () -> new BadRequestException(exceptionMessage)));
    }

    public void createOptionCmp(Set<OptionCmpDto> optionCmpDtos, Set<Long> elementCmpIds) {
        for(Long elementCmpId : elementCmpIds){
            // Busca a categoria
            ElementCmp elementCmp = elementCmpRepository.findById(elementCmpId).orElseThrow(() -> new BadRequestException("Section not found"));

            // Configura a seção nos elementos
            Set<OptionCmpDto> optionCmpDtosWithElement = optionCmpDtos.stream()
                    .peek(dto -> dto.setElementCmp(elementCmp.getElementCmpId()))
                    .collect(Collectors.toSet());
            // Converte e persiste os elementos
            Set<OptionCmp> newOptions = OptionCmpMapper.INSTANCE.toOptionCmp(optionCmpDtosWithElement);
            newOptions.forEach(optionCmpRepository::save);
        }
    }

//    public void updateOptionCmp(OptionCmpPutDto optionCmpPutDto, Long optionCmpId) {
//        // Faz alteracoes no produto.
//        // Busca a categoria
//        OptionCmp optionCmp = optionCmpRepository.findById(optionCmpId).orElseThrow(() -> new BadRequestException("Element not found"));
//        ElementCmp elementCmp = optionCmp.getElementCmp();
//        optionCmpPutDto.setElementCmpId(elementCmp.getElementCmpId().longValue());
//
//        OptionCmp  OptionBeUpdated = OptionCmpMapper.INSTANCE.toOptionCmp(optionCmpPutDto);
//        OptionBeUpdated.setOptionCmpId(optionCmpId);
//        // Persiste alteracoes.
//        optionCmpRepository.save(OptionBeUpdated);
//    }


    public void deleteOptionCmpById(Long optionCmpId) {
        // Econtra produto ou joga exceção.
        findOptionByIdOrThrowBadRequestException(optionCmpId, "Element not found");
        // Deleta produto via id.
        optionCmpRepository.deleteById(optionCmpId);
    }
}
