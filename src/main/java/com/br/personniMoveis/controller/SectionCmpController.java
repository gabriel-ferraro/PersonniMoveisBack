package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpGetDto;
import com.br.personniMoveis.service.productCmp.SectionCmpService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("SectionCmp")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-key")
public class SectionCmpController {

    private final SectionCmpService sectionCmpService;

    @Autowired
    public SectionCmpController(SectionCmpService sectionCmpService) {
        this.sectionCmpService = sectionCmpService;

    }

    @GetMapping
    public ResponseEntity<List<SectionCmpGetDto>> getAllSectionCmp() {
        List<SectionCmpGetDto> sectionDtos = sectionCmpService.getAllSections();
        if (sectionDtos.isEmpty()) {
            return ResponseEntity.ok(sectionDtos); // Retorna uma lista vazia
        }
        return ResponseEntity.ok(sectionDtos);
    }

    @GetMapping(path = "/{cateogoryId}")
    public ResponseEntity<SectionCmpGetDto> getSectionCmpById (@PathVariable("cateogoryId") Long cateogoryId) {
        return ResponseEntity.ok(sectionCmpService.findSectionByIdOrThrowBadRequestException(cateogoryId, "Category not found"));
    }

    @PostMapping(path = "/{cateogoryId}")
    public ResponseEntity<String> createSectionCmp(@RequestBody @Valid SectionCmpDto sectionCmpDtos, @PathVariable("cateogoryId") Long cateogoryId) {
        sectionCmpService.createSectionCmp(sectionCmpDtos, cateogoryId);
        return new ResponseEntity(HttpStatus.CREATED);
    }



//    @PutMapping(path = "/{sectionCmpId}")
//    public ResponseEntity updateSectionCmp(@RequestBody @Valid Set<SectionCmpPutDto> sectionCmpPutDto, @PathVariable("sectionCmpId") Long sectionCmpId) {
//        sectionCmpService.updateSectionCmp(sectionCmpPutDto, sectionCmpId);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping(path = "/{sectionCmpId}")
    public ResponseEntity deleteSectionCmpById(@PathVariable("sectionCmpId") Long sectionCmpId) {
        sectionCmpService.deleteSectionById(sectionCmpId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
