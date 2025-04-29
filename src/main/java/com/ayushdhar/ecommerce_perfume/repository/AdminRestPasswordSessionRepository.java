package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.AdminRestPasswordSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRestPasswordSessionRepository extends JpaRepository<AdminRestPasswordSession, String> {
    void deleteAdminRestPasswordSessionByAdminUserId(String adminUserId);

}
