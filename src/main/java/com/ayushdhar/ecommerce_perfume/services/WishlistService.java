package com.ayushdhar.ecommerce_perfume.services;

import com.ayushdhar.ecommerce_perfume.entity.ProductOnBucketId;
import com.ayushdhar.ecommerce_perfume.entity.ProductOnWishList;
import com.ayushdhar.ecommerce_perfume.repository.ProductOnWishListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private final ProductOnWishListRepository productOnWishListRepository;

    public WishlistService(ProductOnWishListRepository productOnWishListRepository) {
        this.productOnWishListRepository = productOnWishListRepository;
    }

    public Optional<ProductOnWishList> findProductOnWishlistById(ProductOnBucketId productOnBucketId) {
        return productOnWishListRepository.findById(productOnBucketId);
    }

    @Transactional
    public void saveNewProductOnWishList(ProductOnBucketId productOnBucketId) {
        ProductOnWishList productOnWishList = new ProductOnWishList();
        productOnWishList.setId(productOnBucketId);
        productOnWishListRepository.save(productOnWishList);
    }

    @Transactional
    public void deleteProductOnWishListById(ProductOnBucketId productOnBucketId) {
        productOnWishListRepository.deleteById(productOnBucketId);
    }

    public List<ProductOnWishList> findAllProductOnWishListByUserId(String userId) {
        return productOnWishListRepository.findByUserId(userId);
    }

}
