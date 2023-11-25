package com.br.personniMoveis.service;

import com.br.personniMoveis.model.store.StoreProperties;
import com.br.personniMoveis.repository.StorePropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class StorePropertiesService {

    private final StorePropertiesRepository storePropertiesRepository;

    @Autowired
    public StorePropertiesService(StorePropertiesRepository storePropertiesRepository) {
        this.storePropertiesRepository = storePropertiesRepository;
    }

    public StoreProperties getStore() {
        return storePropertiesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Erro ao carregar dados da config da loja"));
    }

    //@Transactional
    public StoreProperties createStore() {
        StoreProperties store = new StoreProperties();
        store.setStoreId(1L);
        return storePropertiesRepository.save(store);
    }

    /**
     * Atualiza todas customizações possíveis para configs do e-commerce.
     *
     * @param sp Dto de alterações.
     */
    public StoreProperties updateStore(StoreProperties sp) {
        // Obtém a instância do repositório ou cria uma nova se não existir
//        StoreProperties store = storePropertiesRepository.findById(1L).orElseGet(StoreProperties::new);
        StoreProperties store = storePropertiesRepository.findById(sp.getStoreId()).orElse(createStore());
        store = new StoreProperties(sp);
        if(store.getStoreLogoPath() != null) {
            try {
                String url = UploadDriveService.updateDriveFile(store.getStoreLogoPath(), store.getStoreName());
                store.setStoreLogoPath(url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(store.getStoreSecondaryImgPath() != null) {
            try {
                String url = UploadDriveService.updateDriveFile(store.getStoreSecondaryImgPath(), store.getStoreName());
                store.setStoreSecondaryImgPath(url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(store.getStorePlaceholdeImgPath() != null) {
            try {
                String url = UploadDriveService.updateDriveFile(store.getStorePlaceholdeImgPath(), store.getStoreName());
                store.setStorePlaceholdeImgPath(url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return storePropertiesRepository.save(store);
    }

}