package com.br.personniMoveis.controller;

import com.br.personniMoveis.service.product.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sections")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @DeleteMapping(path = "/{sectionId}")
    public ResponseEntity<HttpStatus> deleteSection(@PathVariable("sectionId") Long sectionId) {
        sectionService.deleteSection(sectionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
