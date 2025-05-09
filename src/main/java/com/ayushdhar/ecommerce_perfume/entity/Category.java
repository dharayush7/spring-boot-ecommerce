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
@Table(name = "category")
public class Category {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Boolean isTag = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ProductOnCategories> productOnCategories;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid();
        }
    }
}

