package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.productCmp.SectionCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository

public interface SectionCmpRepository extends JpaRepository<SectionCmp, Long>, JpaSpecificationExecutor<SectionCmp> {

    Set<SectionCmp> findByCategoryId(Long categoryId);

}