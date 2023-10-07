package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.user.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<ClientAddress, Long> {

}
