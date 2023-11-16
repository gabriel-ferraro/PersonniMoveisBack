package com.br.personniMoveis.service;

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

    public StoreProperties getStore() {
        return storePropertiesRepository.findById(1L).orElse(this.createStore());
    }

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
        StoreProperties result = null;
        // Só deve haver uma configuração persistida, então registro único deve possuir id 1.
        if (storePropertiesRepository.findById(1L).isEmpty()) {
            result = this.createStore();
        } else {
            StoreProperties store = this.getStore();
            // Se de alguma forma id foi alterado, seta para 1 antes de salvar.
            store.setStoreId(1L);
            result = storePropertiesRepository.save(store);
        }
        return result;
    }
}
