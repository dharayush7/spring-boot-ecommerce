package com.ayushdhar.ecommerce_perfume.controller;


import com.ayushdhar.ecommerce_perfume.dto.address.AddAddressRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.address.UpdateAddressRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.Address;
import com.ayushdhar.ecommerce_perfume.entity.User;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.UserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.AddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addNewAddress(@Valid @RequestBody AddAddressRequestDTO addAddressRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            User user = UserContext.get();

            addressService.addAddressByDTO(addAddressRequestDTO, user.getId());

            response.setMsg("Address added successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateAddress(@Valid @RequestBody UpdateAddressRequestDTO updateAddressRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            User user = UserContext.get();

            Optional<Address> optionalAddress = addressService.findAddressById(updateAddressRequestDTO.getId());

            if(optionalAddress.isEmpty()) {
                response.setMsg("Address not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Address address = optionalAddress.get();
            address.setCity(updateAddressRequestDTO.getCity());
            address.setCountry(updateAddressRequestDTO.getCountry());
            address.setState(updateAddressRequestDTO.getState());
            address.setFirstName(updateAddressRequestDTO.getFirstName());
            address.setLastName(updateAddressRequestDTO.getLastName());
            address.setPhoneNumber(updateAddressRequestDTO.getPhoneNumber());
            address.setAlternatePhoneNumber(updateAddressRequestDTO.getAlternatePhoneNumber());
            address.setAddress1(updateAddressRequestDTO.getAddress1());
            address.setAddress2(updateAddressRequestDTO.getAddress2());
            address.setLandmark(updateAddressRequestDTO.getLandmark());
            address.setPostCode(updateAddressRequestDTO.getPostCode());

            addressService.updateAddress(address);

            response.setMsg("Address updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Address>>> getAddressByUserId() {
        ApiResponse<List<Address>> response = new ApiResponse<>();
        try {
            User user = UserContext.get();
            List<Address> addresses = new ArrayList<>();

            for (Address address : addressService.findAllAddressesByUserId(user.getId()) ) {
                address.setUser(null);
                addresses.add(address);
            }


            response.setData(addresses);
            response.setMsg("Addresses listed");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@RequestParam String id) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            User user = UserContext.get();
            Optional<Address> optionalAddress = addressService.findAddressById(id);

            if(optionalAddress.isEmpty()) {
                response.setMsg("Address not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Address address = optionalAddress.get();

            if (!address.getUser().getId().equals(user.getId())) {
                response.setMsg("Unauthorized");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            addressService.deleteAddress(address);

            response.setMsg("Address deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
