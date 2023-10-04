package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByEmail(String email); //Método responsavel por fazer a consulta pelo usuario
    // no banco de dados.
}
