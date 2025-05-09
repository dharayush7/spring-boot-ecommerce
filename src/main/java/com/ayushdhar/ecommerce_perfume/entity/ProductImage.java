package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;


@Data
@NoArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    private String id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(optional = true)
    @JoinColumn(name = "productId", nullable = true)
    private Product product;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid(); // Implement or simulate cuid generation
        }
    }
}

