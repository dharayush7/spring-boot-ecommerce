package com.ayushdhar.ecommerce_perfume.services;

import com.ayushdhar.ecommerce_perfume.entity.AdminSession;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.Otp;
import com.ayushdhar.ecommerce_perfume.repository.AdminSessionRepository;
import com.ayushdhar.ecommerce_perfume.repository.AdminUserRepository;
import com.ayushdhar.ecommerce_perfume.repository.OtpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final AdminUserRepository adminUserRepository;
    private final OtpRepository otpRepository;
    private final AdminSessionRepository adminSessionRepository;

    public AuthService(
            AdminUserRepository adminUserRepository,
            OtpRepository otpRepository,
            AdminSessionRepository adminSessionRepository
    ) {
        this.adminUserRepository = adminUserRepository;
        this.otpRepository = otpRepository;
        this.adminSessionRepository = adminSessionRepository;
    }

    public Optional<AdminUser> findByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }

    public Optional<AdminUser> findUserById(String id)
    {
        return adminUserRepository.findById(id);
    }

    @Transactional
    public void deleteAllOtpsForUser(String adminUserId) {
        otpRepository.deleteByAdminUserId(adminUserId);
    }

    @Transactional
    public void saveNewOtp(String adminUserId, Integer code) {
        Otp otp = new Otp();
        otp.setCode(code);
        otp.setAdminUserId(adminUserId);
        otpRepository.save(otp);
    }

    public List<Otp> findAllOtpsForUser(String adminUserId) {
        return otpRepository.findByAdminUserIdOrderByCreatedAtDesc(adminUserId);
    }

    @Transactional
    public void deleteAllAdminSessionsByAdminUserId(String adminUserId)  {
        adminSessionRepository.deleteAdminSessionByAdminUserId(adminUserId);
    }

    @Transactional
    public AdminSession saveNewAdminSession(String adminUserId) {
        AdminSession adminSession = new AdminSession();
        adminSession.setAdminUserId(adminUserId);
        return adminSessionRepository.save(adminSession);
    }

}
