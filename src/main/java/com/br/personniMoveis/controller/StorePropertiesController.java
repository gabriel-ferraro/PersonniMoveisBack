package com.br.personniMoveis.controller;

import com.br.personniMoveis.service.StorePropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    
//    @PutMapping(path = "/{name}")
//    public ResponseEntity setStoreName(@PathVariable("name") String storeName) {
//        storePropertiesService.setStoreName(storeName);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//    
//    @PutMapping(path = "logo")
//    public ResponseEntity setStoreLogoImage(@PathVariable("storeLogoPath") String storeLogoPath) {
//        storePropertiesService.setStoreLogoImage(storeLogoPath);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//    
//    @PutMapping(path = "main-collor/{hex}")
//    public ResponseEntity setStorePrimaryCollor(@PathVariable("hex") String hexColor) {
//        storePropertiesService.setPrimaryCollor(hexCollor);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//    
//    @PutMapping(path = "secondary-collor/{hex}")
//    public ResponseEntity setStoreSecondaryCollor(@PathVariable("hex") String hexColor) {
//        storePropertiesService.setSecondaryCollor(hexCollor);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//    @PutMapping(path = "email/{storeEmail}")
//    public ResponseEntity setStoreSecondaryCollor(@PathVariable("storeEmail") String storeEmail) {
//        storePropertiesService.setStoreEmail(storeEmail);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
}
