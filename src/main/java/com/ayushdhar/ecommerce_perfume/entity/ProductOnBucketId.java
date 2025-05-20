package com.ayushdhar.ecommerce_perfume.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ProductOnBucketId implements Serializable {
    private String productId;
    private String userId;
}
