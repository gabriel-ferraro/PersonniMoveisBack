package com.br.personniMoveis.service.product;

import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.product.Section;
import com.br.personniMoveis.repository.SectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Section findSectionOrThrowNotFoundException(Long sectionId) {
        return sectionRepository.findById(sectionId).orElseThrow(
                () -> new ResourceNotFoundException("Não foi possível encontrar a seção."));
    }

    @Transactional
    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public void deleteSection(Long sectionId) {
        sectionRepository.delete(this.findSectionOrThrowNotFoundException(sectionId));
    }
}
