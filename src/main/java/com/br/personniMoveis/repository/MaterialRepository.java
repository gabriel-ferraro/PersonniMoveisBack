package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.product.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

//    List<Material> findMaterialsByProductsProductId(Long id);
}
