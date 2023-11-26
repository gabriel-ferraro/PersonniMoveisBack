package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByEmail(String email); // Método responsavel por fazer a consulta pelo usuario no banco de dados.

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    UserEntity findUserByEmail(String email);

    List<UserEntity> findByIsRemovedFalse();
}
