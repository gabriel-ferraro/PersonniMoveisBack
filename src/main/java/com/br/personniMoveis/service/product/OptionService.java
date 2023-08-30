package com.br.personniMoveis.service.product;

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

    public Option createOption(Option option) {
        return optionRepository.save(option);
    }
}
