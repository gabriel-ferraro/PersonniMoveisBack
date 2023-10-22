package com.br.personniMoveis.controller;

import com.br.personniMoveis.service.product.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("options")
@CrossOrigin(origins = "*")
public class OptionControlller {

    private final OptionService optionService;

    @Autowired
    public OptionControlller(OptionService optionService) {
        this.optionService = optionService;
    }

    @DeleteMapping(path = "/{optionId}")
    public ResponseEntity<HttpStatus> deleteOption(@PathVariable("optionId") Long optionId) {
        this.optionService.deleteOption(optionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
