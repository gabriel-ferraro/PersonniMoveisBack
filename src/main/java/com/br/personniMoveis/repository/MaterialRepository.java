package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.product.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    
}
