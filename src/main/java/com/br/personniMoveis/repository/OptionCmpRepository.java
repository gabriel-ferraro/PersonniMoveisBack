package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.productCmp.OptionCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository

public interface OptionCmpRepository extends JpaRepository<OptionCmp, Long>, JpaSpecificationExecutor<OptionCmp> {
    Set<OptionCmp> findByElementCmp(Long elementCmp);

}