package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.mapper.User.UserEntityMapper;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Autowired
    private UserRepository userRepository;

    public UserEntity createAccount(UserCreateAccountDto userCreateAccountDto) {
//        UserEntity newAccount = userRepository.save(UserEntityMapper.INSTANCE
//                .userCreateAccountDtoToUserEntity(userCreateAccountDto));
        var cryptPassword = passwordEncoder.encode(userCreateAccountDto.getPassword());
//        UserEntity newAccount = UserEntityMapper.INSTANCE
//                .userCreateAccountDtoToUserEntity(userCreateAccountDto);
        var user = new UserEntity(userCreateAccountDto.getName(), userCreateAccountDto.getEmail(),
        cryptPassword, userCreateAccountDto.getCpf(), userCreateAccountDto.getPhoneNumber());
        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "User not found"));
    }
}
