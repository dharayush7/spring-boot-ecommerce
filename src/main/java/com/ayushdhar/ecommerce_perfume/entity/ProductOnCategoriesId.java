package com.ayushdhar.ecommerce_perfume.entity;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ProductOnCategoriesId implements Serializable {
    private String productId;
    private String categoryId;
}