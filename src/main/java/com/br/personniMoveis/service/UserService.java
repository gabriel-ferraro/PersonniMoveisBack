package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserEntity createAccount(UserCreateAccountDto userCreateAccountDto) {
//        UserEntity newAccount = userRepository.save(UserEntityMapper.INSTANCE
//                .userCreateAccountDtoToUserEntity(userCreateAccountDto));
        var cryptPassword = passwordEncoder.encode(userCreateAccountDto.getPassword());
//        UserEntity newAccount = UserEntityMapper.INSTANCE
//                .userCreateAccountDtoToUserEntity(userCreateAccountDto);
        var user = new UserEntity(userCreateAccountDto.getName(), userCreateAccountDto.getEmail(),
        cryptPassword, userCreateAccountDto.getCpf(), userCreateAccountDto.getPhoneNumber(), userCreateAccountDto.getProfile());
        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity findUserOrThrowNotFoundException(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
