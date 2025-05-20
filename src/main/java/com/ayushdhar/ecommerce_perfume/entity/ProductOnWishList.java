package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_on_wishlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOnWishList {
    @EmbeddedId
    private ProductOnBucketId id;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

}
