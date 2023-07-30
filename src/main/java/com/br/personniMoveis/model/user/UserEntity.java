package com.br.personniMoveis.model.user;

import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.constant.UserEntityRoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Mapemaneto ORM da entidade "usuário" do sistema. Implementa interface
 * UserDetails do Spring Boot para ser desse tipo e se beneficiar da
 * reutilização de código para autenticação e autorização.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    @Column(unique = true)
    private String cpf;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "user_entity_role")
    @Enumerated(EnumType.ORDINAL)
    private UserEntityRoleType userEntityRole;

    @JsonIgnore
    @OneToMany(mappedBy = "clientAddress")
    @Setter(AccessLevel.NONE)
    private final ArrayList<ClientAddress> addresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "clientOrder")
    @Setter(AccessLevel.NONE)
    private final ArrayList<ClientOrder> orders = new ArrayList<>();

    /**
     * Lista de espera de produtos indisponíveis na loja dos quais o cliente
     * aguarda o retorno.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "")
    @Setter(AccessLevel.NONE)
    private final ArrayList<Product> productWaitingList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Cada usuário deve ter somente um papel (role) -> ADMIN, USER ou COLLABORATOR, 
        // como indicado no enum UserEntityRoleType, por isso retorna uma lista contendo o papel único do usuário.
        return Collections.singletonList(new SimpleGrantedAuthority(userEntityRole.name()));
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Default.
        return true;
    }

    /**
     * Implementação para validar se credenciais (token JWT) não expirou.
     * Diferene dos outros métodos implementados da interface UserDetails, esse
     * é o único que tem um tratamento específico, os outros possuem
     * implementações simplificadas de propósito, pois não interferem ou
     * beneficiam a lógica de negócio de nosso sistema -> Isso pode mudar
     * futuramente.
     *
     * @return Verdadeiro ou falso para credenciais expiradas.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        // a fazer
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Default.
        return true;
    }
}
