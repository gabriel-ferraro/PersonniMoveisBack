package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.NewPassDto;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.dto.User.UserGetDto;
import com.br.personniMoveis.dto.UserAdminInfo;
import com.br.personniMoveis.dto.UserUpdateInfoDto;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.mapper.User.UserEntityMapper;
import com.br.personniMoveis.model.user.ClientAddress;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.repository.AddressRepository;
import com.br.personniMoveis.repository.UserRepository;
import com.br.personniMoveis.utils.AuthUtils;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final AuthUtils authUtils;
    private final AddressRepository addressRepository;
    private final UserEntityMapper userEntityMapper;
    private final TokenService tokenService;
    private final EmailService emailService;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AddressService addressService,
                       AuthUtils authUtils, AddressRepository addressRepository, UserEntityMapper userEntityMapper,
                       TokenService tokenService, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.authUtils = authUtils;
        this.addressRepository = addressRepository;
        this.userEntityMapper = userEntityMapper;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    /**
     * Deve retornar um true caso tudo ocorra bem.
     */
    public boolean validateAccount(UserEntity user) {
        boolean result = true;
        try {
            // Tudo ocorreu bem.
            emailService.validateAccount(user.getEmail(), user.getName(), tokenService.generateConfirmationToken(user));
        } catch (Exception e) {
            //
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return result;
    }

    public void changePassword(String userEmail) {
        try {
            emailService.changePassword(userEmail, tokenService.generateUpdatePasswordToken(userEmail));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public UserEntity createAccount(UserCreateAccountDto data) {
        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        var user = new UserEntity(data);
        user.setPassword(encryptedPassword);
        user.setIsRemoved(false);
        return userRepository.save(user);
    }

    public void saveUser(UserEntity user) {
        user.setIsRemoved(false);
        userRepository.save(user);
    }

    @Transactional
    public ClientAddress createAddress(String token, ClientAddress newAddress) {
        // Adquire id do usuário via token e recebe endereço como arg.
        Long userId = authUtils.getUserId(token);
        UserEntity user = this.findUserOrThrowNotFoundException(userId);
        ClientAddress address = addressService.createAddress(newAddress);
        //relaciona endereço com usuário.
        user.getAddresses().add(address);
        address.setClientAddress(user);
        // Salva relação.
        userRepository.save(user);
        return newAddress;
    }

    public void updatePassword(NewPassDto newPassDto) {
        UserEntity user = userRepository.findUserByEmail(tokenService.getClaimFromToken(newPassDto.getToken(), "email"));
        String encryptedPassword = passwordEncoder.encode(newPassDto.getUpdatedPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public List<ClientAddress> getAllUserAddresses(String token) {
        UserEntity user = this.findUserOrThrowNotFoundException(authUtils.getUserId(token));
        return user.getAddresses();
    }

    public UserEntity adminCreateAccount(UserAdminCreateAccountDto userAdminCreateAccountDto) {
        String encryptedPassword = passwordEncoder.encode(userAdminCreateAccountDto.getPassword());
        var adminUser = new UserEntity(userAdminCreateAccountDto);
        adminUser.setPassword(encryptedPassword);
        adminUser.setIsRemoved(false);
        return userRepository.save(adminUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = findUserOrThrowNotFoundException(userId);
                user.setIsRemoved(true);
        userRepository.save(user);
    }

    public List<UserGetDto> getAllUsers() {
        return userRepository.findByIsRemovedFalse().stream().map(UserEntityMapper.INSTANCE::UserEntityToUserGetDto).toList();
    }

    public ClientAddress getSingleAddress(String token, Long addressId) {
        Long userId = authUtils.getUserId(token);
        UserEntity user = this.findUserOrThrowNotFoundException(userId);
        return user.getAddressById(addressId);
    }

    @Transactional
    public void updateAddress(String token,ClientAddress updatedAddress) {
        Long userId = authUtils.getUserId(token);
        UserEntity user = findUserOrThrowNotFoundException(userId);

        updatedAddress.setClientAddress(user);

        if (updatedAddress != null) {
          addressRepository.save(updatedAddress);
        } else {
            throw new ResourceNotFoundException("Endereço não encontrado ou não pertence ao usuário.");
        }
    }

    @Transactional
    public void deleteUserAddress(String token, Long addressId) {
        Long userId = authUtils.getUserId(token);
        UserEntity user = findUserOrThrowNotFoundException(userId);
        ClientAddress address = user.getAddressById(addressId);
        addressRepository.deleteById(address.getAddressId());
    }

    public void updateUserInfo(UserUpdateInfoDto userUpdateInfoDto, String token) throws ChangeSetPersister.NotFoundException {
        Long userId = authUtils.getUserId(token);
        UserEntity existingUser = userRepository.findById(userId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        UserEntity updatedUser = userEntityMapper.userGetInfoDtoToUser(userUpdateInfoDto);
        updatedUser.setUserId(userId);
        String currentPassword = userUpdateInfoDto.getCurrentPassword();
        String newPassword = userUpdateInfoDto.getNewPassword();


        // Criptografar a nova senha, se fornecida
        if (newPassword != null && !newPassword.isEmpty()) {
            // Verificar se a senha atual corresponde à senha armazenada
            if (!passwordEncoder.matches(currentPassword, existingUser.getPassword())) {
                throw new RuntimeException("A senha atual fornecida está incorreta");
            }
            // Criptografar a nova senha
            String newEncryptedPassword = passwordEncoder.encode(newPassword);
            existingUser.updatePassword(newEncryptedPassword);
        }
        existingUser.updateFromDto(updatedUser);
        userRepository.save(existingUser);
    }

    public void AdminUpdateUserInfo(UserAdminInfo userAdminInfo) {
        var user = userRepository.getReferenceById(userAdminInfo.getUserId());
        user.AdminUpdateInfo(userAdminInfo);
        userRepository.save(user);
    }

    public UserGetDto getUser(String token) {
        Long Id = authUtils.getUserId(token);
        UserEntity user = this.findUserOrThrowNotFoundException(Id);
        return UserEntityMapper.INSTANCE.UserEntityToUserGetInfoDto(user);
    }

    public UserEntity findUserOrThrowNotFoundException(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
