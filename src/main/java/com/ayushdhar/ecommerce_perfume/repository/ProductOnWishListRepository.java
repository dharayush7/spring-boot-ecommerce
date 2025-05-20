package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.ProductOnBucketId;
import com.ayushdhar.ecommerce_perfume.entity.ProductOnWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOnWishListRepository extends JpaRepository<ProductOnWishList, ProductOnBucketId> {
    List<ProductOnWishList> findByUserId(String userId);
}
