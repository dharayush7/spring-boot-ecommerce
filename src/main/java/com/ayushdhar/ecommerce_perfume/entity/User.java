package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String uid;

    @Column(nullable = false, unique = true, length = 16)
    private String mobileNumber;

    @Column()
    private String name = "user";

    @Column( nullable = true, unique = true)
    private String email;

    @Column
    private Boolean isEmailVerified = false;

    @Column
    private Boolean blocked = false;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid();
        }
    }
}
