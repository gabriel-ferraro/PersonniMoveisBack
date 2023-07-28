package com.br.personniMoveis.model;

import com.br.personniMoveis.constant.UserEntityRoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mapeament para papéis "roles" dos usuários do sistema.
 * Cada usuário SÓ DEVE TER UM de 3 papéis, como expresso no enum UserEntityRole:
 * 0 - ADMIN,
 * 1 - COLLABORATOR,
 * 2 - USER;
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_entity_role")
public class UserEntityRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Enumerated
    private UserEntityRoleType role;
}
