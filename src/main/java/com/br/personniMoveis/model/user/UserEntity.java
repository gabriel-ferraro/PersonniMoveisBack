package com.br.personniMoveis.model.user;

import com.br.personniMoveis.constant.UserEntityRoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String cpf;

    @Column(name = "phone_number")
    private String phoneNumber;

    public UserEntity(String name, String email, String password, String cpf, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
    }

//    @Column(name = "user_entity_role")
//    @Enumerated(EnumType.ORDINAL)
//    private UserEntityRoleType userEntityRole;

//    @JsonIgnore
//    @OneToMany(mappedBy = "clientAddress")
//    @Setter(AccessLevel.NONE)
//    private final ArrayList<ClientAddress> addresses = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    @JsonIgnore
//    @OneToMany(mappedBy = "clientOrder")
//    @Setter(AccessLevel.NONE)
//    private final ArrayList<ClientOrder> orders = new ArrayList<>();

    /**
     * Lista de espera de produtos indisponíveis na loja dos quais o cliente
     * aguarda o retorno.
     */
//    @JsonIgnore
//    @OneToMany(mappedBy = "productWaiting")
//    @Setter(AccessLevel.NONE)
//    private final HashSet<Product> productWaitingList = new HashSet<>();


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

}
