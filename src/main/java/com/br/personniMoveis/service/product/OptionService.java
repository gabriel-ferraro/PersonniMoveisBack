package com.br.personniMoveis.service.product;

import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.product.Option;
import com.br.personniMoveis.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public Option findOptionOrThrowNotFoundException(Long optionId) {
        return optionRepository.findById(optionId).orElseThrow(
                () -> new ResourceNotFoundException("Não foi possível encontrar a opção."));
    }

    public Option saveOption(Option option) {
        return optionRepository.save(option);
    }

    public void deleteOption(Long optionId) {
        optionRepository.delete(this.findOptionOrThrowNotFoundException(optionId));
    }
}
