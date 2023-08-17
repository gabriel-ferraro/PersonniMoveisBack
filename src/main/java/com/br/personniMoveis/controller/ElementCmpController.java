package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.ElementsDto.ElementCmpGetDto;
import com.br.personniMoveis.dto.ElementsDto.ElementCmpPostDto;
import com.br.personniMoveis.dto.ElementsDto.ElementCmpPutDto;
import com.br.personniMoveis.service.ElementCmpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("ElementCmp")
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
    public ResponseEntity<String> createElementCmp(@RequestBody @Valid ElementCmpPostDto elementCmpPostDto, @PathVariable("sectionCmpId") Long sectionCmpId) {
        elementCmpService.createElementCmp(elementCmpPostDto, sectionCmpId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{sectionCmpId}")
    public ResponseEntity updateElementCmp(@RequestBody @Valid ElementCmpPutDto elementCmpPutDto, @PathVariable("sectionCmpId") Long sectionCmpId) {
        elementCmpService.updateElementCmp(elementCmpPutDto, sectionCmpId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{sectionCmpId}")
    public ResponseEntity deleteElementById(@PathVariable("sectionCmpId") Long sectionCmpId) {
        elementCmpService.deleteElementCmpById(sectionCmpId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}