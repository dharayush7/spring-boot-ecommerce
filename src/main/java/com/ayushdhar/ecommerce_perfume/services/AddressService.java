package com.ayushdhar.ecommerce_perfume.services;

import com.ayushdhar.ecommerce_perfume.dto.address.AddAddressRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.Address;
import com.ayushdhar.ecommerce_perfume.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public void addAddressByDTO(AddAddressRequestDTO addAddressRequestDTO, String userId) {


        Address address = new Address();
        address.setUserId(userId);
        address.setFirstName(addAddressRequestDTO.getFirstName());
        address.setLastName(addAddressRequestDTO.getLastName());
        address.setCity(addAddressRequestDTO.getCity());
        address.setState(addAddressRequestDTO.getState());
        address.setCountry(addAddressRequestDTO.getCountry());
        address.setAddress1(addAddressRequestDTO.getAddress1());
        address.setAddress2(addAddressRequestDTO.getAddress2());
        address.setLandmark(addAddressRequestDTO.getLandmark());
        address.setPhoneNumber(addAddressRequestDTO.getPhoneNumber());
        address.setAlternatePhoneNumber(addAddressRequestDTO.getAlternatePhoneNumber());
        address.setPostCode(addAddressRequestDTO.getPostCode());
        addressRepository.save(address);

    }

    @Transactional
    public void updateAddress(Address address) {
        addressRepository.save(address);
    }

    public Optional<Address> findAddressById(String id) {
        return addressRepository.findById(id);
    }

    public List<Address> findAllAddressesByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteAddress(Address address) {
        addressRepository.delete(address);
    }
}
