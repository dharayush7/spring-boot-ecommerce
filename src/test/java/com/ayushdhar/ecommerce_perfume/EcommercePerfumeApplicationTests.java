package com.ayushdhar.ecommerce_perfume;

import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.lib.Utils;
import com.ayushdhar.ecommerce_perfume.repository.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EcommercePerfumeApplicationTests {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    void contextLoads() {
        AdminUser adminUser = new AdminUser();
        Utils utils = new Utils();
        adminUser.setEmail("contact@ayushdhar.com");
        adminUser.setPassword(utils.encodePassword("123456"));
        adminUser.setMobileNo("9593487117");
        adminUser.setIsOwner(true);
        adminUser.setName("Ayush Dhar");
        List<String> permission = List.of("ADMIN");
        adminUser.setPermission(permission);
        AdminUser user = adminUserRepository.save(adminUser);
        System.out.println(user);
    }
}
