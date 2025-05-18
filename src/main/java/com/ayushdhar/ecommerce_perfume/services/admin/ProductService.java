package com.ayushdhar.ecommerce_perfume.services.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.product.AddProductRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.product.UpdateProductRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.*;
import com.ayushdhar.ecommerce_perfume.repository.AdminUserRepository;
import com.ayushdhar.ecommerce_perfume.repository.ProductImageRepository;
import com.ayushdhar.ecommerce_perfume.repository.ProductOnCategoriesRepository;
import com.ayushdhar.ecommerce_perfume.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final AdminUserRepository adminUserRepository;
    private final ProductOnCategoriesRepository productOnCategoriesRepository;
    private final ProductImageRepository productImageRepository;

    public ProductService(
            ProductRepository productRepository,
            AdminUserRepository adminUserRepository,
            ProductOnCategoriesRepository productOnCategoriesRepository,
            ProductImageRepository productImageRepository
    ) {
        this.productRepository = productRepository;
        this.adminUserRepository = adminUserRepository;
        this.productOnCategoriesRepository = productOnCategoriesRepository;
        this.productImageRepository = productImageRepository;
    }

    public Optional<AdminUser> findAdminUserById(String id) {
        return adminUserRepository.findById(id);
    }

    @Transactional
    public void addProductByDto(AddProductRequestDTO addProductRequestDTO) {
        Product product = new Product();
        product.setName(addProductRequestDTO.getName());
        product.setDescription(addProductRequestDTO.getDescription());
        product.setFragrance(addProductRequestDTO.getFragrance());
        product.setDescription2(addProductRequestDTO.getDescription2());
        product.setDescription3(addProductRequestDTO.getDescription3());
        product.setIdealFor(addProductRequestDTO.getIdealFor());
        product.setMaxPrice(addProductRequestDTO.getMaxPrice());
        product.setPoints(addProductRequestDTO.getPoints());
        product.setPreference(addProductRequestDTO.getPreference());
        product.setQuantity(addProductRequestDTO.getQuantity());
        product.setStrength(addProductRequestDTO.getStrength());
        product.setType(addProductRequestDTO.getType());
        product.setSustainable(addProductRequestDTO.getSustainable());
        product.setSellPrice(addProductRequestDTO.getSellPrice());
        productRepository.save(product);

        for (String categoryId : addProductRequestDTO.getCategory()) {
            ProductOnCategories productOnCategories = new ProductOnCategories();
            ProductOnCategoriesId productOnCategoriesId = new ProductOnCategoriesId();

            productOnCategoriesId.setProductId(product.getId());
            productOnCategoriesId.setCategoryId(categoryId);

            productOnCategories.setId(productOnCategoriesId);
            productOnCategoriesRepository.save(productOnCategories);
        }

        for (String imageId : addProductRequestDTO.getImages()) {
            ProductImage productImage = productImageRepository.findById(imageId).get();
            productImage.setProductId(product.getId());
            productImageRepository.save(productImage);
        }
    }

    @Transactional
    public void updateProductByDto(UpdateProductRequestDTO updateProductRequestDTO) {
        Product product = productRepository.findById(updateProductRequestDTO.getId()).get();
        product.setName(updateProductRequestDTO.getName());
        product.setDescription(updateProductRequestDTO.getDescription());
        product.setFragrance(updateProductRequestDTO.getFragrance());
        product.setDescription2(updateProductRequestDTO.getDescription2());
        product.setDescription3(updateProductRequestDTO.getDescription3());
        product.setIdealFor(updateProductRequestDTO.getIdealFor());
        product.setMaxPrice(updateProductRequestDTO.getMaxPrice());
        product.setPoints(updateProductRequestDTO.getPoints());
        product.setPreference(updateProductRequestDTO.getPreference());
        product.setQuantity(updateProductRequestDTO.getQuantity());
        product.setStrength(updateProductRequestDTO.getStrength());
        product.setType(updateProductRequestDTO.getType());
        product.setSustainable(updateProductRequestDTO.getSustainable());
        productRepository.save(product);

        List<String> categoriesId = updateProductRequestDTO.getCategory();

        for (ProductOnCategories productOnCategories : product.getProductOnCategories()) {
            boolean isPresent = false;
            for (String categoryId : categoriesId) {
                if (productOnCategories.getId().getCategoryId().equals(categoryId)) {
                    isPresent = true;
                    categoriesId.remove(categoryId);
                    break;
                }
            }

            if (!isPresent) {
                productOnCategoriesRepository.delete(productOnCategories);
            }
        }

        for (String categoryId : categoriesId) {
            ProductOnCategories productOnCategories = new ProductOnCategories();
            ProductOnCategoriesId productOnCategoriesId = new ProductOnCategoriesId();

            productOnCategoriesId.setProductId(product.getId());
            productOnCategoriesId.setCategoryId(categoryId);

            productOnCategories.setId(productOnCategoriesId);
            productOnCategoriesRepository.save(productOnCategories);
        }

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(String id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void updateProduct(Product product) {
        productRepository.save(product);
    }
}
