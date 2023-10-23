package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.user.OrderItemCmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemCmpRepository extends JpaRepository<OrderItemCmp, Long> {

}
