package com.br.personniMoveis.model;

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
    
    @Column(unique = true)
    private String phoneNumber;
    
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
}
