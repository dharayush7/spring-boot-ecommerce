package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String description2;

    @Column
    private String description3;

    @ElementCollection
    @Column(nullable = false)
    private List<String> points;

    @Column(nullable = false)
    private Float maxPrice;

    @Column(nullable = false)
    private Float sellPrice;

    @Column
    private String fragrance;

    @Column
    private String strength;

    @Column
    private String preference;

    @Column
    private String sustainable;

    @Column
    private String type;

    @Column
    private String idealFor;

    @Column
    private Integer quantity;

    @Column(nullable = false)
    private Boolean isLive = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOnCategories> productOnCategories;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid();
        }
    }
}
