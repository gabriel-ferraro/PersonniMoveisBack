package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpGetDto;
import com.br.personniMoveis.service.productCmp.ElementCmpService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ElementCmp")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-key")
public class ElementCmpController {

    private final ElementCmpService elementCmpService;

    @Autowired
    public ElementCmpController(ElementCmpService elementCmpService) {
        this.elementCmpService = elementCmpService;
    }

    @GetMapping
    public ResponseEntity<List<ElementCmpGetDto>> getAllElementCmp() {
        List<ElementCmpGetDto> elementDtos = elementCmpService.getAllSections();
        if (elementDtos.isEmpty()) {
            return ResponseEntity.ok(elementDtos); // Retorna uma lista vazia
        }
        return ResponseEntity.ok(elementDtos);
    }

    @GetMapping(path = "/{sectionCmpId}")
    public ResponseEntity<ElementCmpGetDto> getElementCmpById (@PathVariable("sectionCmpId") Long sectionCmpId) {
        return ResponseEntity.ok(elementCmpService.findElementByIdOrThrowBadRequestException(sectionCmpId, "Section not found"));
    }

    @PostMapping(path = "/{sectionCmpId}")
    public ResponseEntity<String> createElementCmp(@RequestBody @Valid ElementCmpDto elementCmpDtos, @PathVariable("sectionCmpId") Long sectionCmpId) {
        elementCmpService.createElementCmp(elementCmpDtos, sectionCmpId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

//    @PutMapping(path = "/{sectionCmpId}")
//    public ResponseEntity updateElementCmp(@RequestBody @Valid ElementCmpPutDto elementCmpPutDto, @PathVariable("sectionCmpId") Long sectionCmpId) {
//        elementCmpService.updateElementCmp(elementCmpPutDto, sectionCmpId);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping(path = "/{sectionCmpId}")
    public ResponseEntity deleteElementById(@PathVariable("sectionCmpId") Long sectionCmpId) {
        elementCmpService.deleteElementById(sectionCmpId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
