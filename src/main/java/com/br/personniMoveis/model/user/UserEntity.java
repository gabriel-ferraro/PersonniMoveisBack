package com.br.personniMoveis.model.user;

import com.br.personniMoveis.constant.Profiles;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.model.product.Product;
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
import java.util.Objects;

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

    private Profiles profile;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "address_id")
    private final List<ClientAddress> addresses = new ArrayList<>();

    /**
     * Lista de espera por produtos que não estão disponíveis. Serve como controle para notificar clientes quando se
     * tornarem disponíveis.
     */
    @ManyToMany
    @JoinTable(name = "user_waiting_product", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productWaitingList;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.profile == Profiles.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_COLLABORATOR"), new SimpleGrantedAuthority("ROLE_USER"));
        } else if (this.profile == Profiles.COLLABORATOR) {
            return List.of(new SimpleGrantedAuthority("ROLE_COLLABORATOR"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
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
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
