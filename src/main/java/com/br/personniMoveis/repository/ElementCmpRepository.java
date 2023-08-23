package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.productCmp.ElementCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementCmpRepository extends JpaRepository<ElementCmp, Long>, JpaSpecificationExecutor<ElementCmp> {

}
