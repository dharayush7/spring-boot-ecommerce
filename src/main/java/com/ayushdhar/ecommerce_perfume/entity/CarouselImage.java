package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;

@Data
@Entity
@Table(name = "carousel_image")
@NoArgsConstructor
@AllArgsConstructor
public class CarouselImage {
    @Id
    private  String id;

    @Column(nullable = false)
    private String url;

    @Column
    private String carouselId;

    @OneToOne(optional = true)
    @JoinColumn(name = "carouselId", referencedColumnName = "id", insertable = false, updatable = false)
    private Carousel carousel;


    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = generateCuid(); // Simulate cuid()
        }
    }
}
