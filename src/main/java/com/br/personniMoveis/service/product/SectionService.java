package com.br.personniMoveis.service.product;

import com.br.personniMoveis.model.product.Option;
import com.br.personniMoveis.model.product.Section;
import com.br.personniMoveis.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final OptionService optionService;

    @Autowired
    public SectionService(SectionRepository sectionRepository, OptionService optionService) {
        this.sectionRepository = sectionRepository;
        this.optionService = optionService;
    }

    public Section createSection(Section section) {
        return sectionRepository.save(section);
    }

    public Option createOption(Option option) {
        return optionService.createOption(option);
    }
}
