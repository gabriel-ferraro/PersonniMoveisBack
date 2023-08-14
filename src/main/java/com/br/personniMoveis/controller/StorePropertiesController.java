package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.StorePropertiesDto;
import com.br.personniMoveis.service.StorePropertiesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controladora da loja. Define configurações do site como estilo e propriedades gerais.
 */
@RestController
@RequestMapping("store")
public class StorePropertiesController {
    
    private final StorePropertiesService storePropertiesService;
    
    @Autowired
    public StorePropertiesController(StorePropertiesService storePropertiesService) {
        this.storePropertiesService = storePropertiesService;
    }
    
    @PutMapping
    public ResponseEntity<HttpStatus> updateStoreProperties(@Valid StorePropertiesDto storePropertiesDto) {
        storePropertiesService.updateStore(storePropertiesDto);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
