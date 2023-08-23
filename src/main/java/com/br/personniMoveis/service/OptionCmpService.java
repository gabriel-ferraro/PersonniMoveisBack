package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpGetDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPostDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPutDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpGetDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPostDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPutDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.ElementCmp.ElementCmpMapper;
import com.br.personniMoveis.mapper.OptionCmp.OptionCmpMapper;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.OptionCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void createOptionCmp(OptionCmpPostDto optionCmpPostDto, Long elementCmpId) {
        // Busca a categoria
        ElementCmp elementCmp = elementCmpRepository.findById(elementCmpId).orElseThrow(() -> new BadRequestException("Section not found"));
        //seta a categoria na seção
        optionCmpPostDto.setElementCmpId(elementCmp.getElementCmpId());

        OptionCmp newOption = OptionCmpMapper.INSTANCE.toOptionCmp(optionCmpPostDto);

        // Persiste a nova instância no banco de dados
        optionCmpRepository.save(newOption);
    }

    public void updateOptionCmp(OptionCmpPutDto optionCmpPutDto, Long optionCmpId) {
        // Faz alteracoes no produto.
        // Busca a categoria
        OptionCmp optionCmp = optionCmpRepository.findById(optionCmpId).orElseThrow(() -> new BadRequestException("Element not found"));
        ElementCmp elementCmp = optionCmp.getElementCmp();
        optionCmpPutDto.setElementCmpId(elementCmp.getElementCmpId().longValue());

        OptionCmp  OptionBeUpdated = OptionCmpMapper.INSTANCE.toOptionCmp(optionCmpPutDto);
        OptionBeUpdated.setOptionCmpId(optionCmpId);
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
