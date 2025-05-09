package com.ayushdhar.ecommerce_perfume.controller.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.category.CreateCategoryRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.category.GetAllCategoriesResponseDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.category.UpdateCategoryRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.Category;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.AdminUserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createCategory(@Valid @RequestBody CreateCategoryRequestDTO createCategoryRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = categoryService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals(Constants.CATEGORY))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            categoryService.createCategory(createCategoryRequestDTO);
            response.setMsg("Category created successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<GetAllCategoriesResponseDTO>>> getAllCategories() {
        ApiResponse<List<GetAllCategoriesResponseDTO>> response = new ApiResponse<>();
        try {
            AdminUser admin = categoryService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals(Constants.CATEGORY))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            List<Category> categories = categoryService.getAllCategories();
            List<GetAllCategoriesResponseDTO> listOfCategories = new ArrayList<>();

            for (Category category : categories) {
                GetAllCategoriesResponseDTO getAllCategoriesResponseDTO = new GetAllCategoriesResponseDTO();
                getAllCategoriesResponseDTO.setId(category.getId());
                getAllCategoriesResponseDTO.setName(category.getName());
                getAllCategoriesResponseDTO.setCount(category.getProductOnCategories().size());
                listOfCategories.add(getAllCategoriesResponseDTO);
            }

            response.setMsg("Category fetch successfully");
            response.setData(listOfCategories);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateCategory(@Valid @RequestBody UpdateCategoryRequestDTO updateCategoryRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = categoryService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals(Constants.CATEGORY))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<Category> optionalCategory = categoryService.findCategoryById(updateCategoryRequestDTO.getId());

            if (optionalCategory.isEmpty()) {
                response.setMsg("Category not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Category category = optionalCategory.get();
            category.setName(updateCategoryRequestDTO.getName());
            category.setIsTag(updateCategoryRequestDTO.getIsTag());
            category.setDescription(updateCategoryRequestDTO.getDesc());

            categoryService.updateCategory(category);
            response.setMsg("Category updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
