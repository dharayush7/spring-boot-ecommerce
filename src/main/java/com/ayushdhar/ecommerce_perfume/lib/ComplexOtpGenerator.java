package com.ayushdhar.ecommerce_perfume.lib;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ComplexOtpGenerator {
    private ComplexOtpGenerator() {
        throw new IllegalStateException("Utility class");
    }
    private static final SecureRandom secureRandom = new SecureRandom();

    public static int generateOtp() {
        long randomNumber = secureRandom.nextLong();
        byte[] hash = sha256(Long.toString(randomNumber));
        int hashCode = Math.abs(bytesToInt(hash));
        return 100000 + (hashCode % 900000);
    }

    private static byte[] sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("SHA-256 algorithm not available", e);
        }
    }

    private static int bytesToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < Math.min(4, bytes.length); i++) {
            value = (value << 8) + (bytes[i] & 0xff);
        }
        return value;
    }
}
