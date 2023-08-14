package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.StorePropertiesDto;
import com.br.personniMoveis.model.store.StoreProperties;
import com.br.personniMoveis.repository.StorePropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorePropertiesService {

    private final StorePropertiesRepository storePropertiesRepository;

    @Autowired
    public StorePropertiesService(StorePropertiesRepository storePropertiesRepository) {
        this.storePropertiesRepository = storePropertiesRepository;
    }

    public void updateStore(StorePropertiesDto spd) {
        // Só deve haver uma configuração persistida, então registro único deve possuir id 1.
        if(storePropertiesRepository.findById(1L).isEmpty()) {

        }
    }

    public StoreProperties createStore(StoreProperties spd) {
        spd.setStoreId(1L);
        return storePropertiesRepository.save(spd);
    }
}
