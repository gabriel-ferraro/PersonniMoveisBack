package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.productCmp.OptionCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository

public interface OptionCmpRepository extends JpaRepository<OptionCmp, Long>, JpaSpecificationExecutor<OptionCmp> {

}