package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.user.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Adquire todos os pedidos realizados pelo cliente selecionado via id.
     * @param userId Id do cliente.
     * @return Todos os pedidos realizados pelo cliente.
     */
    @Query("SELECT o FROM Order o LEFT JOIN o.user u WHERE u.id = :userId")
    List<Order> getUserOrders(Long userId);
}
