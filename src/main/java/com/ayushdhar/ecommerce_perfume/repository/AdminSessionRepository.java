package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.AdminSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdminSessionRepository extends JpaRepository<AdminSession, String> {
    public void deleteAllByAdminUserId(String adminUserId);
    public Optional<AdminSession> findByAdminUserId(String adminUserId);
    public Optional<AdminSession> findBySessionToken(String sessionToken);
}
