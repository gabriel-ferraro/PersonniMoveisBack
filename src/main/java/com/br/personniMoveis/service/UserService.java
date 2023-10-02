package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.dto.User.UserGetDto;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.mapper.User.UserEntityMapper;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserEntity findUserOrThrowNotFoundException(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public UserEntity createAccount(UserCreateAccountDto data) {
//        UserEntity newAccount = userRepository.save(UserEntityMapper.INSTANCE
//                .userCreateAccountDtoToUserEntity(userCreateAccountDto));
        String encryptedPassword = passwordEncoder.encode(data.getPassword());

//        data.setPassword(encryptedPassword);

//        userCreateAccountDto.setProfile(userCreateAccountDto.getProfile());
//        UserEntity newAccount = UserEntityMapper.INSTANCE
//                .userCreateAccountDtoToUserEntity(userCreateAccountDto);
        // Profile - caso não insira nenhum valor no campo profile ele retorna como valor padrão USER
//        Profiles profile = (userCreateAccountDto.getProfile() != null) ? userCreateAccountDto.getProfile() : Profiles.USER;

        var user = new UserEntity(data);
        user.setPassword(encryptedPassword);
//        passwordEncoder.encode(user.getPassword());
        return userRepository.save(user);
    }

    public UserEntity adminCreateAccount(UserAdminCreateAccountDto userAdminCreateAccountDto) {
        String encryptedPassword = passwordEncoder.encode(userAdminCreateAccountDto.getPassword());
        var adminUser = new UserEntity(userAdminCreateAccountDto);
        adminUser.setPassword(encryptedPassword);
        return userRepository.save(adminUser);
    }

    public List<UserGetDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserEntityMapper.INSTANCE::UserEntityToUserGetDto).toList();
    }

    public List<UserEntity> getClientsWaitingForProduct(Long productId) {
        return userRepository.getClientsWaitingForProduct(productId);
    }
}
