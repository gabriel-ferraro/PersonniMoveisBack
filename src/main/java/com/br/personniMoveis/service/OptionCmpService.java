package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpGetDto;
import com.br.personniMoveis.exception.BadRequestException;
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

    public void createOptionCmp(OptionCmpDto optionCmpDtos, Long elementCmpId) {
            // Busca a categoria
            ElementCmp elementCmp = elementCmpRepository.findById(elementCmpId).orElseThrow(() -> new BadRequestException("Section not found"));
            // Converte e persiste os elementos
            OptionCmp newOption = OptionCmpMapper.INSTANCE.toOptionCmp(optionCmpDtos);
            // Configura a seção nos elementos
            newOption.setElementCmpId(elementCmpId);
            optionCmpRepository.save(newOption);
    }

    public void updateOptionCmp(OptionCmpDto optionCmpDto, Long optionCmpId) {
        // Busca a opção
        OptionCmp optionCmp = optionCmpRepository.findById(optionCmpId).orElseThrow(() -> new BadRequestException("Element not found"));
        OptionCmp  OptionBeUpdated = OptionCmpMapper.INSTANCE.toOptionCmp(optionCmpDto);
        OptionBeUpdated.setId(optionCmpId);
        OptionBeUpdated.setElementCmpId(optionCmp.getElementCmpId()); // Mantém o mesmo elemento

        // Persiste alteracoes.
        optionCmpRepository.save(OptionBeUpdated);
    }


    public void deleteOptionCmpById(Long optionCmpId) {
        // Econtra produto ou joga exceção.
        findOptionByIdOrThrowBadRequestException(optionCmpId, "Element not found");
        // Deleta produto via id.
        optionCmpRepository.deleteById(optionCmpId);
    }
}
