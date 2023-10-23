package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.user.OrderCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCmpRepository extends JpaRepository<OrderCmp, Long> {
}
