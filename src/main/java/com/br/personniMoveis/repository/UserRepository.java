package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //Método responsavel por fazer a consulta pelo usuario no banco de dados.
    UserDetails findByEmail(String email);

    //@Query("SELECT u FROM UserEntity LEFT JOIN u.productWaitingList p WHERE p.id = :productId")
    @Query("SELECT u FROM UserEntity u") // terminar metodo...
    List<UserEntity> getClientsWaitingForProduct(Long productId);
}
