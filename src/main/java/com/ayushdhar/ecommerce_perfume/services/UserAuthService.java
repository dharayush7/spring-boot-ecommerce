package com.ayushdhar.ecommerce_perfume.services;

import com.ayushdhar.ecommerce_perfume.dto.auth.LoginRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.User;
import com.ayushdhar.ecommerce_perfume.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService {
    private final UserRepository userRepository;
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUserByDTO(LoginRequestDTO loginRequestDTO) {
        Optional<User> optionalUser = userRepository.findByUid(loginRequestDTO.getUid());
        if (optionalUser.isPresent()) {
            return;
        }

        User user = new User();
        user.setUid(loginRequestDTO.getUid());
        user.setMobileNumber(loginRequestDTO.getMobileNo());
        userRepository.save(user);
    }
}
