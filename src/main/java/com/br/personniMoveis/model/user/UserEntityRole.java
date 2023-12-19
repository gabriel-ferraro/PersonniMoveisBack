package com.br.personniMoveis.model.user;

import com.br.personniMoveis.enums.Profiles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mapeament ORM para papéis (roles) dos usuários do sistema.
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
    private Profiles role;
}
