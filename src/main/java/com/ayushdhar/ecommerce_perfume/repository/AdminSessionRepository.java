package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.AdminSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AdminSessionRepository extends JpaRepository<AdminSession, String> {
    public void deleteAdminSessionsByAdminUserId(String adminUserId);

}
