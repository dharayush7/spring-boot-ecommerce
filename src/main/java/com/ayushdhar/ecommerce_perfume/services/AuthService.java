package com.ayushdhar.ecommerce_perfume.services;

import com.ayushdhar.ecommerce_perfume.entity.AdminRestPasswordSession;
import com.ayushdhar.ecommerce_perfume.entity.AdminSession;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.Otp;
import com.ayushdhar.ecommerce_perfume.repository.AdminRestPasswordSessionRepository;
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
    private final AdminRestPasswordSessionRepository adminRestPasswordSessionRepository;

    public AuthService(
            AdminUserRepository adminUserRepository,
            OtpRepository otpRepository,
            AdminSessionRepository adminSessionRepository,
            AdminRestPasswordSessionRepository adminRestPasswordSessionRepository
    ) {
        this.adminUserRepository = adminUserRepository;
        this.otpRepository = otpRepository;
        this.adminSessionRepository = adminSessionRepository;
        this.adminRestPasswordSessionRepository = adminRestPasswordSessionRepository;
    }

    public Optional<AdminUser> findByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }

    public Optional<AdminUser> findUserById(String id)
    {
        return adminUserRepository.findById(id);
    }

    @Transactional
    public void updateAdminUser(AdminUser adminUser) {
        adminUserRepository.save(adminUser);
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

    @Transactional
    public void saveNewOtpForRestPasswordSession(String adminUserId, Integer code, String adminRestPasswordSessionId) {
        Otp otp = new Otp();
        otp.setCode(code);
        otp.setAdminUserId(adminUserId);
        otp.setAdminRestPasswordSessionId(adminRestPasswordSessionId);
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

    @Transactional
    public void deleteAllAdminRestPasswordSessionsByAdminUserId(String adminUserId) {
        adminRestPasswordSessionRepository.deleteAdminRestPasswordSessionByAdminUserId(adminUserId);
    }

    @Transactional
    public AdminRestPasswordSession saveNewAdminRestPasswordSession(String adminUserId) {
        AdminRestPasswordSession adminRestPasswordSession = new AdminRestPasswordSession();
        adminRestPasswordSession.setAdminUserId(adminUserId);
        return adminRestPasswordSessionRepository.save(adminRestPasswordSession);
    }

    public Optional<AdminRestPasswordSession> findAdminRestPasswordSessionById(String id) {
        return adminRestPasswordSessionRepository.findById(id);
    }

    @Transactional
    public void updateAdminRestPasswordSession(AdminRestPasswordSession adminRestPasswordSession) {
        adminRestPasswordSessionRepository.save(adminRestPasswordSession);
    }
}
