package com.br.personniMoveis.service;

import com.br.personniMoveis.model.StoreProperties;
import com.br.personniMoveis.repository.StorePropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class StorePropertiesService {
    
    private final StorePropertiesRepository storePropertiesRepository;
    
    @Autowired
    public StorePropertiesService(StorePropertiesRepository storePropertiesRepository) {
        this.storePropertiesRepository = storePropertiesRepository;
    }
    
//    public ResponseEntity setStoreName(@RequestBody StoreProperties StoreProperties) {
//        storePropertiesRepository.;
//    }
//    
//    public ResponseEntity setStoreLogoImage(String storeLogoPath) {
//        
//    }
//    
//    public ResponseEntity setStorePrimaryCollor(String hexColor) {
//        storePropertiesRepository.;
//    }
//    
//    public ResponseEntity setStoreSecondaryCollor(String hexColor) {
//        storePropertiesRepository.;
//    }
//    
//    public ResponseEntity setStoreEmail(String storeEmail) {
//        storePropertiesRepository.;
//    }
}
