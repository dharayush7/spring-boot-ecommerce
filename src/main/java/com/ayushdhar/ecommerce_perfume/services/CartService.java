package com.ayushdhar.ecommerce_perfume.services;

import com.ayushdhar.ecommerce_perfume.entity.Product;
import com.ayushdhar.ecommerce_perfume.entity.ProductOnBucketId;
import com.ayushdhar.ecommerce_perfume.entity.ProductOnCart;
import com.ayushdhar.ecommerce_perfume.repository.ProductOnCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final ProductOnCartRepository productOnCartRepository;

    public CartService(ProductOnCartRepository productOnCartRepository) {
        this.productOnCartRepository = productOnCartRepository;
    }

    public Optional<ProductOnCart> findById(ProductOnBucketId productOnBucketId) {
        return productOnCartRepository.findByProductIdAndUserId(productOnBucketId.getProductId(), productOnBucketId.getUserId());
    }

    @Transactional
    public void addProductOnCart(ProductOnBucketId productOnBucketId, int quantity) {
        ProductOnCart productOnCart = new ProductOnCart();

        productOnCart.setQuantity(quantity);
        productOnCart.setId(productOnBucketId);
        productOnCartRepository.save(productOnCart);
    }

    public List<ProductOnCart> getProductsOnCart(String userId) {
        return productOnCartRepository.findByUserId(userId);

    }

    @Transactional
    public void increaseProductQuantity(ProductOnBucketId productOnBucketId) {
        Optional<ProductOnCart> optionalProductOnCart = findById(productOnBucketId);
        ProductOnCart productOnCart = optionalProductOnCart.get();

        productOnCart.setQuantity(productOnCart.getQuantity() + 1);
        productOnCartRepository.save(productOnCart);
    }

    @Transactional
    public void decreaseProductQuantity(ProductOnBucketId productOnBucketId) {
        Optional<ProductOnCart> optionalProductOnCart = findById(productOnBucketId);
        ProductOnCart productOnCart = optionalProductOnCart.get();

        if (productOnCart.getQuantity() <= 0) {
            return;
        }
        productOnCart.setQuantity(productOnCart.getQuantity() -1);
        productOnCartRepository.save(productOnCart);
    }

    @Transactional
    public void switchTypeOnCart(ProductOnBucketId productOnBucketId) {
        Optional<ProductOnCart> optionalProductOnCart = findById(productOnBucketId);
        ProductOnCart productOnCart = optionalProductOnCart.get();
        productOnCart.setIsSaveForLater(!productOnCart.getIsSaveForLater());
        productOnCartRepository.save(productOnCart);
    }

    @Transactional
    public void deleteProductOnCart(ProductOnBucketId productOnBucketId) {
        productOnCartRepository.deleteById(productOnBucketId);
    }
}
