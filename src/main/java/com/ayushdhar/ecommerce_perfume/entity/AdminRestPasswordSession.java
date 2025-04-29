package com.ayushdhar.ecommerce_perfume.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;
import java.util.List;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;

@Entity
@Table(name = "admin_rest_password_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRestPasswordSession {

    @Id
    private String id;

    @Column(nullable = false)
    private String adminUserId;

    @Column(nullable = false)
    private Boolean isVarfied = false;

    @Column(nullable = false)
    private Boolean isChanged = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT (now() + INTERVAL '15 minutes')")
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private LocalDateTime expireAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminUserId", insertable = false, updatable = false)
    private AdminUser adminUser;

    @OneToMany(mappedBy = "adminRestPasswordSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Otp> otp;


    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid();
        }
    }
}
