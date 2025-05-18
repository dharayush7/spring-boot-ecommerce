package com.ayushdhar.ecommerce_perfume.services.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.upload.UploadRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.CarouselImage;
import com.ayushdhar.ecommerce_perfume.entity.ProductImage;
import com.ayushdhar.ecommerce_perfume.repository.AdminUserRepository;
import com.ayushdhar.ecommerce_perfume.repository.CarouselImageRepository;
import com.ayushdhar.ecommerce_perfume.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UploadService {
    private final ProductImageRepository productImageRepository;
    private final CarouselImageRepository carouselImageRepository;
    private final AdminUserRepository adminUserRepository;

    public UploadService(
            ProductImageRepository productImageRepository,
            AdminUserRepository adminUserRepository,
            CarouselImageRepository carouselImageRepository
    ) {
        this.productImageRepository = productImageRepository;
        this.adminUserRepository = adminUserRepository;
        this.carouselImageRepository = carouselImageRepository;
    }

    public Optional<AdminUser> findAdminUserById(String id) {
        return adminUserRepository.findById(id);
    }

    @Transactional
    public ProductImage savaProductImageByDTO (UploadRequestDTO uploadRequestDTO) {
        ProductImage productImage = new ProductImage();
        productImage.setUrl(uploadRequestDTO.getUrl());
        productImageRepository.save(productImage);
        return productImage;
    }

    @Transactional
    public CarouselImage savaCarouselImageByDTO (UploadRequestDTO uploadRequestDTO) {
        CarouselImage carouselImage = new CarouselImage();
        carouselImage.setUrl(uploadRequestDTO.getUrl());
        carouselImageRepository.save(carouselImage);
        return carouselImage;
    }

}
