package com.br.personniMoveis.model.user;

import com.br.personniMoveis.constant.Profiles;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
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

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = true)
    private Profiles profile;

//    public UserEntity(String name, String email, String password, String cpf, String phoneNumber, Profiles profile) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.cpf = cpf;
//        this.phoneNumber = phoneNumber;
//        this.profile = profile;
//    }

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "address_id")
    private final List<ClientAddress> addresses = new ArrayList<>();

    public UserEntity(UserCreateAccountDto data) {
        this.name = data.getName();
        this.email = data.getEmail();
        this.password = data.getPassword();
        this.cpf = data.getCpf();
        this.phoneNumber = data.getPhoneNumber();
        this.profile = Profiles.USER;
    }

    public UserEntity(UserAdminCreateAccountDto data) {
        this.name = data.getName();
        this.email = data.getEmail();
        this.password = data.getPassword();
        this.cpf = data.getCpf();
        this.phoneNumber = data.getPhoneNumber();
        this.profile = data.getProfile();
    }

//    public UserEntity(UserCreateAccountDto data, String cryptPassword) {
//        this.name = data.getName();
//        this.email = data.getEmail();
//        this.password = cryptPassword;
//        this.cpf = data.getCpf();
//        this.phoneNumber = data.getPhoneNumber();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        if(this.profile == Profiles.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_COLLABORATOR"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        else if (this.profile == Profiles.COLLABORATOR) {
            return List.of(new SimpleGrantedAuthority("ROLE_COLLABORATOR"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
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
