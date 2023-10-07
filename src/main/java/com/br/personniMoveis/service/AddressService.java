package com.br.personniMoveis.service;

import com.br.personniMoveis.model.user.ClientAddress;
import com.br.personniMoveis.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public ClientAddress createAddress(ClientAddress newAddress) {
        return addressRepository.save(newAddress);
    }
}
