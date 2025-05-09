package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;

@Data
@Entity
@Table(name = "admin_session")
@NoArgsConstructor
@AllArgsConstructor
public class AdminSession {

    @Id
    private String id;
    @Column(nullable = false)
    private String adminUserId;
    @Column(nullable = false, unique = true)
    private String sessionToken;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, columnDefinition = "timestamp default now() + interval '1 hour'")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime expireAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "adminUserId", referencedColumnName = "id", insertable = false, updatable = false)
    private AdminUser adminUser;


    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid();
        }
        if (sessionToken == null) {
            sessionToken = generateCuid();
        }
    }
}
