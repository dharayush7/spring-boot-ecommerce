package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
}
