package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;

@Data
@Table(name = "admin_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile_no")
})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AdminUser {

    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String mobileNo;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private List<String> permission;

    @Column(nullable = false)
    private Boolean isOwner = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "adminUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Otp> otps;

    @OneToMany(mappedBy = "adminUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AdminSession> adminSessions;

    @OneToMany(mappedBy = "adminUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdminRestPasswordSession> adminRestPasswordSessions;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid(); // Simulate cuid()
        }
        if (isOwner == null) {
            isOwner = false;
        }
    }

}
