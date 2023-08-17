package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.productCmp.SectionCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository

public interface SectionCmpRepository extends JpaRepository<SectionCmp, Long>, JpaSpecificationExecutor<SectionCmp> {

}