package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.mapper.User.UserEntityMapper;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createAccount(UserCreateAccountDto userCreateAccountDto) {
//        UserEntity newAccount = userRepository.save(UserEntityMapper.INSTANCE
//                .userCreateAccountDtoToUserEntity(userCreateAccountDto));
        UserEntity newAccount = UserEntityMapper.INSTANCE
                .userCreateAccountDtoToUserEntity(userCreateAccountDto);
        return userRepository.save(newAccount);
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
