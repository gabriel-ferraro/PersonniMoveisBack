package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpGetDto;
import com.br.personniMoveis.service.productCmp.OptionCmpService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("OptionCmp")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-key")
public class OptionCmpController {

    private final OptionCmpService optionCmpService;

    @Autowired
    public OptionCmpController(OptionCmpService optionCmpService) {
        this.optionCmpService = optionCmpService;
    }

    @GetMapping
    public ResponseEntity<List<OptionCmpGetDto>> getAllOptionCmp() {
        List<OptionCmpGetDto> optionDtos = optionCmpService.getAllOptions();
        if (optionDtos.isEmpty()) {
            return ResponseEntity.ok(optionDtos); // Retorna uma lista vazia
        }
        return ResponseEntity.ok(optionDtos);
    }

    @GetMapping(path = "/{elementCmpId}")
    public ResponseEntity<OptionCmpGetDto> getOptionCmpById (@PathVariable("elementCmpId") Long elementCmpId) {
        return ResponseEntity.ok(optionCmpService.findOptionByIdOrThrowBadRequestException(elementCmpId, "Element not found"));
    }

    @PostMapping(path = "/{elementCmpId}")
    public ResponseEntity<String> createOptionCmp(@RequestBody @Valid OptionCmpDto optionCmpDtos, @PathVariable("elementCmpId") Long elementCmpId) {
        optionCmpService.createOptionCmp(optionCmpDtos, elementCmpId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

//    @PutMapping(path = "/{optionCmpId}")
//    public ResponseEntity updateElementCmp(@RequestBody @Valid OptionCmpPutDto optionCmpPutDto, @PathVariable("optionCmpId") Long optionCmpId) {
//        optionCmpService.updateOptionCmp(optionCmpPutDto, optionCmpId);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping(path = "/{optionCmpId}")
    public ResponseEntity deleteElementById(@PathVariable("optionCmpId") Long optionCmpId) {
        optionCmpService.deleteOptionCmpById(optionCmpId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
