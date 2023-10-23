package com.br.personniMoveis.controller;

import com.br.personniMoveis.model.store.StoreProperties;
import com.br.personniMoveis.service.StorePropertiesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controladora da loja. Define configurações do site como estilo e propriedades gerais.
 */
@RestController
@RequestMapping("store")
@SecurityRequirement(name = "bearer-key")
public class StorePropertiesController {

    private final StorePropertiesService storePropertiesService;

    @Autowired
    public StorePropertiesController(StorePropertiesService storePropertiesService) {
        this.storePropertiesService = storePropertiesService;
    }

    @GetMapping
    public ResponseEntity<StoreProperties> getStoreProperties() {
        return ResponseEntity.ok(storePropertiesService.getStore());
    }

    @PutMapping
    public ResponseEntity<StoreProperties> updateStoreProperties(@Valid StoreProperties storeProperties) {
        return ResponseEntity.ok(storePropertiesService.updateStore(storeProperties));
    }
}
