package com.ayushdhar.ecommerce_perfume.services;


import com.ayushdhar.ecommerce_perfume.dto.admin.category.CreateCategoryRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.Category;
import com.ayushdhar.ecommerce_perfume.repository.AdminUserRepository;
import com.ayushdhar.ecommerce_perfume.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    public final CategoryRepository categoryRepository;
    public final AdminUserRepository adminUserRepository;

    public CategoryService(CategoryRepository categoryRepository, AdminUserRepository adminUserRepository) {
        this.categoryRepository = categoryRepository;
        this.adminUserRepository = adminUserRepository;
    }

    public Optional<AdminUser> findAdminUserById(String id) {
            return adminUserRepository.findById(id);
    }

    @Transactional
    public void createCategory (CreateCategoryRequestDTO createCategoryRequestDTO) {
        Category category = new Category();
        category.setName(createCategoryRequestDTO.getName());
        category.setDescription(createCategoryRequestDTO.getDesc());
        category.setIsTag(createCategoryRequestDTO.getIsTag());
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
}
