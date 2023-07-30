package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.store.StoreProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorePropertiesRepository extends JpaRepository<StoreProperties, Long> {

}
