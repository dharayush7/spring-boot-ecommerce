package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_on_carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOnCart {
    @EmbeddedId
    private ProductOnBucketId id;

    @Column(nullable = false)
    private int quantity;

    @Column
    private Boolean isSaveForLater = false;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

}
