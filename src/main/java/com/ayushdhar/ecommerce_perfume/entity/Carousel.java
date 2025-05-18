package com.ayushdhar.ecommerce_perfume.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;

@Entity
@Table(name = "carousel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carousel {
    @Id
    private String id;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private Integer preference;

    private Boolean isBlack = true;

    @Column(nullable = false)
    private CarouselButtonPosition position;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "carousel", optional = false)
    private CarouselImage image;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid(); // Simulate cuid()
        }
    }
}
