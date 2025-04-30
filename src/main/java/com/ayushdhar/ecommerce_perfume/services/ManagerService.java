package com.ayushdhar.ecommerce_perfume.services;

import com.ayushdhar.ecommerce_perfume.dto.admin.manager.AddManagerRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.lib.Utils;
import com.ayushdhar.ecommerce_perfume.repository.AdminUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final AdminUserRepository adminUserRepository;

    public ManagerService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    private final Utils utils = new Utils();

    public List<AdminUser> getAllManagers() {
     return  adminUserRepository.findAll();
    }

    public  Optional<AdminUser> findAdminUserById(String id) {
        return adminUserRepository.findById(id);
    }

    @Transactional
    public void addNewManager(AddManagerRequestDTO addManagerRequestDTO) {
        AdminUser adminUser = new AdminUser();
        adminUser.setName(addManagerRequestDTO.getName());
        adminUser.setEmail(addManagerRequestDTO.getEmail());
        adminUser.setMobileNo(addManagerRequestDTO.getMobileNo());
        adminUser.setPassword(utils.encodePassword(addManagerRequestDTO.getPassword()));
        List<String> permission = new ArrayList<>();
        if (addManagerRequestDTO.getAdmin()) permission.add("ADMIN");
        if (addManagerRequestDTO.getProduct()) permission.add("PRODUCT");
        if (addManagerRequestDTO.getOrder()) permission.add("ORDER");
        if (addManagerRequestDTO.getPayment()) permission.add("PAYMENT");
        if (addManagerRequestDTO.getCustomer()) permission.add("CUSTOMER");
        if (addManagerRequestDTO.getSite()) permission.add("SITE");
        adminUser.setPermission(permission);
        adminUser.setIsOwner(false);
        adminUserRepository.save(adminUser);
    }

    public Optional<AdminUser> findManagerByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }

    @Transactional
    public void updateManager(AdminUser adminUser) {
        adminUserRepository.save(adminUser);
    }

    @Transactional
    public void deleteManagerByEmail(String email) {
        adminUserRepository.deleteByEmail(email);
    }
}
