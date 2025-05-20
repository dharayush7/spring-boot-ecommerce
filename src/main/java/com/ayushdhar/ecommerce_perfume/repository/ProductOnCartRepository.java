package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.ProductOnBucketId;
import com.ayushdhar.ecommerce_perfume.entity.ProductOnCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOnCartRepository extends JpaRepository<ProductOnCart, ProductOnBucketId> {
    Optional<ProductOnCart> findByProductIdAndUserId(String productId, String userId);
    List<ProductOnCart> findByUserId(String userId);
    void deleteById(ProductOnBucketId productOnBucketId);
}

