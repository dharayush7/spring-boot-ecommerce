package com.ayushdhar.ecommerce_perfume.services;

import com.ayushdhar.ecommerce_perfume.entity.User;
import com.ayushdhar.ecommerce_perfume.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateProfile(User user) {
        userRepository.save(user);
    }
}
