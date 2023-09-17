package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.productCmp.ProductCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCmpRepository extends JpaRepository<ProductCmp, Long>, JpaSpecificationExecutor<ProductCmp> {

}
