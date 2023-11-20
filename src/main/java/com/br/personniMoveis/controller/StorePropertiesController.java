package com.br.personniMoveis.controller;

import com.br.personniMoveis.model.store.StoreProperties;
import com.br.personniMoveis.service.StorePropertiesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("update-store")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoreProperties> updateStoreProperties(@Valid @RequestBody StoreProperties storeProperties) {
        try {
            StoreProperties updatedStore = storePropertiesService.updateStore(storeProperties);
            return ResponseEntity.ok(updatedStore);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
