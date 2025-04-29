package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;


@Entity
@Table(name = "otp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

    @Id
    private String id;
    @Column(nullable = false)
    private Integer code;
    @Column(nullable = false)
    private String adminUserId;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false, columnDefinition = "timestamp default now() + interval '15 minutes'")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime expireAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminUserId", referencedColumnName = "id", insertable = false, updatable = false)
    private AdminUser adminUser;

    @Column
    private String adminRestPasswordSessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminRestPasswordSessionId", referencedColumnName = "id", insertable = false, updatable = false)
    private AdminRestPasswordSession adminRestPasswordSession;


    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid();
        }
    }
}
