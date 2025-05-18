package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, String> {
}
