package com.ayushdhar.ecommerce_perfume.lib;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

public class Utils {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String generateCuid() {
        return "c" + UUID.randomUUID().toString().replace("-", "").substring(0, 24);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matchesPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public static boolean isExpired(LocalDateTime expireAt) {
        return LocalDateTime.now().isAfter(expireAt);
    }
}
