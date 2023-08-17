package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.model.productCmp.TypeCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository

public interface TypeCmpRepository extends JpaRepository<TypeCmp, Long>, JpaSpecificationExecutor<TypeCmp> {

}