package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.dto.User.UserGetDto;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.mapper.User.UserEntityMapper;
import com.br.personniMoveis.model.user.ClientAddress;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final TokenService tokenService;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AddressService addressService, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.tokenService = tokenService;
    }

    public UserEntity createAccount(UserCreateAccountDto data) {
        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        var user = new UserEntity(data);
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    @Transactional
    public ClientAddress createAddress(String token, ClientAddress newAddress) {
        // Adquire id do usuário via token e recebe endereço como arg.
        Long userId = Long.valueOf(tokenService.getIdFromToken(token));
        UserEntity user = this.findUserOrThrowNotFoundException(userId);
        ClientAddress address = addressService.createAddress(newAddress);
        //relaciona endereço com usuário.
        user.getAddresses().add(address);
        address.setClientAddress(user);
        // Salva relação.
        userRepository.save(user);
        return newAddress;
    }

    public List<ClientAddress> getAllUserAddresses(Long userId) {
        UserEntity user = this.findUserOrThrowNotFoundException(userId);
        return user.getAddresses();
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

    public UserEntity findUserOrThrowNotFoundException(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
