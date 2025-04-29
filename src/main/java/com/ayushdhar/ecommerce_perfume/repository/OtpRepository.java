package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    void deleteByAdminUserId(String adminUserId);
    List<Otp> findByAdminUserIdOrderByCreatedAtDesc(String adminUserId);
}
